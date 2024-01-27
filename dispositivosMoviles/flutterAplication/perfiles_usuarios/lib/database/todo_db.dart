import 'package:perfiles_usuarios/model/todo.dart';
import 'package:sqflite/sqflite.dart';
import 'package:perfiles_usuarios/database/database_service.dart';

class TodoDB {
  final tableName = 'todos';

  Future<void> createTable(Database database) async {
    await database.execute("""CREATE TABLE IF NOT EXISTS $tableName(
      "id" INTEGER NOT NULL,
      "name" TEXT NOT NULL,
      "email" TEXT NOT NULL,
      "imageUrl" TEXT NOT NULL,
      "created_at" INTEGER NOT NULL DEFAULT (cast(strftime('%s','now') as int)),
      "updated_at" INTEGER,
      PRIMARY KEY ("id" AUTOINCREMENT)
    );""");
  }

  Future<int> create(
      {required String name,
        required String email,
        required String imageUrl}) async {
    final database = await DatabaseService().database;
    return await database.rawInsert(
      '''INSERT INTO $tableName (name, email, imageUrl, created_at) VALUES (?, ?, ?, ?)''',
      [name, email, imageUrl, DateTime.now().millisecondsSinceEpoch],
    );
  }

  Future<List<Todo>> fetchAll() async {
    final database = await DatabaseService().database;
    final todos = await database.rawQuery(
        '''SELECT * FROM $tableName ORDER BY COALESCE(updated_at, created_at) DESC''');
    return todos.map((todo) => Todo.fromSqfliteDatabase(todo)).toList();
  }

  Future<Todo> fetchById(int id) async {
    final database = await DatabaseService().database;
    final todo = await database.rawQuery('''SELECT * FROM $tableName WHERE id = ?''', [id]);
    return Todo.fromSqfliteDatabase(todo.first);
  }

  Future<int> update(
      {required int id,
        required String? name,
        required String? email,
        required String? imageUrl}) async {
    final database = await DatabaseService().database;
    return await database.update(
      tableName,
      {
        if (name != null) 'name': name,
        if (email != null) 'email': email,
        if (imageUrl != null) 'imageUrl': imageUrl,
        'updated_at': DateTime.now().millisecondsSinceEpoch,
      },
      where: 'id = ?',
      conflictAlgorithm: ConflictAlgorithm.rollback,
      whereArgs: [id],
    );
  }

  Future<void> delete(int id) async {
    final database = await DatabaseService().database;
    await database.rawDelete('''DELETE FROM $tableName WHERE id = ?''', [id]);
  }
}

