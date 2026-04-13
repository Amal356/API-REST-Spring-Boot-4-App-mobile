import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Liste Étudiants',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const EtudiantListPage(),
    );
  }
}

class Etudiant {
  final int id;
  final String cin;
  final String nom;
  final String dateNaissance;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
  });

  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'],
      cin: json['cin'],
      nom: json['nom'],
      dateNaissance: json['dateNaissance'],
    );
  }
}

class EtudiantListPage extends StatefulWidget {
  const EtudiantListPage({super.key});

  @override
  State<EtudiantListPage> createState() => _EtudiantListPageState();
}

class _EtudiantListPageState extends State<EtudiantListPage> {
  List<Etudiant> etudiants = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchEtudiants();
  }

  Future<void> fetchEtudiants() async {
    final response = await http.get(
     Uri.parse('http://localhost:8080/api/etudiants-simple'),
    );
    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(response.body);
      setState(() {
        etudiants = data.map((e) => Etudiant.fromJson(e)).toList();
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Liste des Étudiants')),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: etudiants.length,
              itemBuilder: (context, index) {
                final e = etudiants[index];
                return Card(
                  margin: const EdgeInsets.all(8),
                  child: ListTile(
                    leading: const Icon(Icons.person),
                    title: Text(e.nom),
                    subtitle: Text(
                      'CIN: ${e.cin}\nDate de naissance: ${e.dateNaissance}',
                    ),
                  ),
                );
              },
            ),
    );
  }
}