import 'package:flutter/material.dart';
import 'package:barcode_scan2/barcode_scan2.dart';

void main() => runApp(const MaterialApp(home: MyApp()));

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String barcode = "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Escaneo de código de barras'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Resultados del escaneo:',
              style: TextStyle(fontSize: 18),
            ),
            Text(
              barcode,
              style: const TextStyle(fontSize: 18),),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: scanBarcode,
              child: const Text('Escanea el código de barras'),
            ),
          ],
        ),
      ),
    );
  }

  Future<void> scanBarcode() async {
    try {
      final ScanResult result = await BarcodeScanner.scan();
        if (result.type == ResultType.Barcode) {
          setState(() => barcode = result.rawContent);
        } else if (result.type == ResultType.Error) {
          setState(() => barcode = 'Error: ${result.rawContent}');
        } else if (result.type == ResultType.Cancelled) {
          setState(() => barcode = 'Cancelado por el usuario');
        }
      } on Exception catch (e) {
        setState(() => barcode = 'Error: $e');
      }
  }

}
