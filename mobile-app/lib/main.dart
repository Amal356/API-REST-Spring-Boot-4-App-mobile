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
  final int? departementId;

  Etudiant({
    required this.id,
    required this.cin,
    required this.nom,
    required this.dateNaissance,
    this.departementId,
  });

  factory Etudiant.fromJson(Map<String, dynamic> json) {
    return Etudiant(
      id: json['id'],
      cin: json['cin'],
      nom: json['nom'],
      dateNaissance: json['dateNaissance'],
      departementId: json['departementId'],
    );
  }
}

class Departement {
  final int id;
  final String nom;

  Departement({required this.id, required this.nom});

  factory Departement.fromJson(Map<String, dynamic> json) {
    return Departement(id: json['id'], nom: json['nom']);
  }
}

class EtudiantListPage extends StatefulWidget {
  const EtudiantListPage({super.key});

  @override
  State<EtudiantListPage> createState() => _EtudiantListPageState();
}

class _EtudiantListPageState extends State<EtudiantListPage> {
  List<Etudiant> etudiants = [];
  List<Etudiant> filteredEtudiants = [];
  List<Departement> departements = [];
  int? selectedDepartementId;
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchDepartementsAndEtudiants();
  }

  Future<void> fetchDepartementsAndEtudiants() async {
    final depResponse = await http.get(
      Uri.parse('http://10.0.2.2:8080/api/departements'),
    );
    final etuResponse = await http.get(
      Uri.parse('http://10.0.2.2:8080/api/etudiants'),
    );

    if (depResponse.statusCode == 200 && etuResponse.statusCode == 200) {
      final List<dynamic> depData = json.decode(depResponse.body);
      final List<dynamic> etuData = json.decode(etuResponse.body);
      final loadedEtudiants = etuData.map((e) => Etudiant.fromJson(e)).toList();

      setState(() {
        departements = depData.map((d) => Departement.fromJson(d)).toList();
        etudiants = loadedEtudiants;
        filteredEtudiants = loadedEtudiants;
        isLoading = false;
      });
    } else {
      setState(() => isLoading = false);
    }
  }

  void filterByDepartement(int? departementId) {
    setState(() {
      selectedDepartementId = departementId;
      if (departementId == null) {
        filteredEtudiants = etudiants;
      } else {
        filteredEtudiants = etudiants
            .where((e) => e.departementId == departementId)
            .toList();
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Liste des Étudiants')),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(12),
                  child: DropdownButtonFormField<int?>(
                    value: selectedDepartementId,
                    decoration: const InputDecoration(
                      labelText: 'Filtrer par département',
                      border: OutlineInputBorder(),
                    ),
                    items: [
                      const DropdownMenuItem<int?>(
                        value: null,
                        child: Text('Tous les départements'),
                      ),
                      ...departements.map(
                        (d) => DropdownMenuItem<int?>(
                          value: d.id,
                          child: Text(d.nom),
                        ),
                      ),
                    ],
                    onChanged: filterByDepartement,
                  ),
                ),
                Expanded(
                  child: ListView.builder(
                    itemCount: filteredEtudiants.length,
                    itemBuilder: (context, index) {
                      final e = filteredEtudiants[index];
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
                ),
              ],
            ),
    );
  }
}