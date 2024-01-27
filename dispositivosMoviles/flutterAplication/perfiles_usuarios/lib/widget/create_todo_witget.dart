import 'package:flutter/material.dart';
import 'package:perfiles_usuarios/model/todo.dart';

class CreateTodoWidget extends StatefulWidget {
  final Todo? todo;
  final ValueChanged<Todo> onSubmit;

  const CreateTodoWidget({
    Key? key,
    this.todo,
    required this.onSubmit,
  }) : super(key: key);

  @override
  State<CreateTodoWidget> createState() => _CreateTodoWidgetState();
}

class _CreateTodoWidgetState extends State<CreateTodoWidget> {
  final nameController = TextEditingController();
  final emailController = TextEditingController();
  final imageUrlController = TextEditingController();
  final formKey = GlobalKey<FormState>();

  @override
  void initState() {
    super.initState();
    if (widget.todo != null) {
      nameController.text = widget.todo!.name;
      emailController.text = widget.todo!.email;
      imageUrlController.text = widget.todo!.imageUrl;
    }
  }

  @override
  Widget build(BuildContext context) {
    final isEditing = widget.todo != null;

    return AlertDialog(
      title: Text(isEditing ? 'Editar Perfil' : 'Crear Perfil'),
      content: Form(
        key: formKey,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            TextFormField(
              controller: nameController,
              autofocus: true,
              decoration: const InputDecoration(
                hintText: 'Nombre',
              ),
              validator: (value) {
                return value != null && value.isEmpty ? 'El nombre no puede estar vacio' : null;
              },
            ),
            TextFormField(
              controller: emailController,
              decoration: const InputDecoration(
                hintText: 'Correo',
              ),
              validator: (value) {
                return value != null && value.isEmpty ? 'Necesitas un correo' : null;
              },
            ),
            TextFormField(
              controller: imageUrlController,
              decoration: const InputDecoration(
                hintText: 'Image URL',
              ),
              validator: (value) {
                return value != null && value.isEmpty ? 'Necesitas una Imagen' : null;
              },
            ),
          ],
        ),
      ),
      actions: [
        TextButton(
          onPressed: () {
            if (formKey.currentState!.validate()) {
              final newTodo = Todo(
                id: widget.todo?.id ?? 0,
                name: nameController.text,
                email: emailController.text,
                imageUrl: imageUrlController.text,
                createdAt: DateTime.now().toIso8601String(),
                updatedAt: widget.todo?.updatedAt,
              );
              widget.onSubmit(newTodo);
            }
          },
          child: const Text('OK'),
        ),
      ],
    );
  }
}
