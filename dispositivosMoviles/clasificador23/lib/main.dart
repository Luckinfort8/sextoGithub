import 'dart:typed_data';
import 'dart:io';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key}) : super(key: key);

  @override
  // ignore: library_private_types_in_public_api
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  late Uint8List _imageBytes = Uint8List(0);
  String _predictedClass = '';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Clasificador de Basura'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0), // Ajusta el espacio alrededor según tus necesidades
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              _imageBytes.isNotEmpty
                  ? Image.memory(
                _imageBytes,
                width: 250,
                height: 250,
                fit: BoxFit.cover,
              )
                  : Container(),
              const SizedBox(height: 50),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Expanded(
                    child: ElevatedButton(
                      onPressed: () async {
                        var pickedImage =
                        await ImagePicker().pickImage(source: ImageSource.gallery);
                        if (pickedImage != null) {
                          var imageBytes = await loadImageAsBytes(pickedImage.path);
                          setState(() {
                            _imageBytes = imageBytes;
                          });

                          var response = await sendImageToServer(imageBytes);
                          var predictedClass = extractPredictedClass(response);
                          setState(() {
                            _predictedClass = predictedClass;
                          });
                        }
                      },
                      child: const Text('Seleccionar y Enviar',
                        style: TextStyle(fontSize: 15),),
                    ),
                  ),
                  const SizedBox(width: 20),
                  Expanded(
                    child: ElevatedButton(
                      onPressed: () async {
                        var pickedImage =
                        await ImagePicker().pickImage(source: ImageSource.camera);
                        if (pickedImage != null) {
                          var imageBytes = await loadImageAsBytes(pickedImage.path);
                          setState(() {
                            _imageBytes = imageBytes;
                          });

                          var response = await sendImageToServer(imageBytes);
                          var predictedClass = extractPredictedClass(response);
                          setState(() {
                            _predictedClass = predictedClass;
                          });
                        }
                      },
                      child: const Text('Tomar Foto y Enviar',
                        style: TextStyle(fontSize: 15),),
                    ),
                  ),
                ],
              ),
              const SizedBox(height: 40),
              const Text(
                'Clasificación:',
                style: TextStyle(fontSize: 25),
              ),
              const SizedBox(height: 10),
              Text(
                _predictedClass,
                style: const TextStyle(fontSize: 35, fontWeight: FontWeight.bold),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Future<String> sendImageToServer(Uint8List imageBytes) async {
    var url = Uri.parse('http://192.168.22.36:5000/predict');
    var request = http.MultipartRequest('POST', url)
      ..files.add(http.MultipartFile.fromBytes('image', imageBytes, filename: 'image.jpg'));

    var response = await http.Response.fromStream(await request.send());
    return response.body;
  }

  Future<Uint8List> loadImageAsBytes(String imagePath) async {
    File imageFile = File(imagePath);
    return await imageFile.readAsBytes();
  }

  String extractPredictedClass(String response) {
    final Map<String, dynamic> jsonResponse = json.decode(response);
    return jsonResponse['predicted_class'] ?? 'No se pudo obtener la clasificación';
  }
}
