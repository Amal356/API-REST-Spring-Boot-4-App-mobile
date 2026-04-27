import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  ActivityIndicator,
  Pressable,
} from 'react-native';

type Etudiant = {
  id: number;
  cin: string;
  nom: string;
  dateNaissance: string;
  departementId?: number;
};

type Departement = {
  id: number;
  nom: string;
};

export default function HomeScreen() {
  const [etudiants, setEtudiants] = useState<Etudiant[]>([]);
  const [filteredEtudiants, setFilteredEtudiants] = useState<Etudiant[]>([]);
  const [departements, setDepartements] = useState<Departement[]>([]);
  const [selectedDep, setSelectedDep] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      fetch('http://10.0.2.2:8080/api/departements').then((res) => res.json()),
      fetch('http://10.0.2.2:8080/api/etudiants').then((res) => res.json()),
    ])
      .then(([deps, etus]) => {
        setDepartements(deps);
        setEtudiants(etus);
        setFilteredEtudiants(etus);
        setLoading(false);
      })
      .catch((err) => console.error(err));
  }, []);

  const applyFilter = (depId: number | null) => {
    setSelectedDep(depId);
    if (depId === null) {
      setFilteredEtudiants(etudiants);
      return;
    }
    setFilteredEtudiants(etudiants.filter((e) => e.departementId === depId));
  };

  if (loading) {
    return (
      <View style={styles.center}>
        <ActivityIndicator size="large" color="#0000ff" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Liste des Étudiants</Text>
      <View style={styles.filters}>
        <Pressable style={styles.filterBtn} onPress={() => applyFilter(null)}>
          <Text style={styles.filterText}>Tous</Text>
        </Pressable>
        {departements.map((d) => (
          <Pressable key={d.id} style={styles.filterBtn} onPress={() => applyFilter(d.id)}>
            <Text style={styles.filterText}>{d.nom}</Text>
          </Pressable>
        ))}
      </View>
      <Text style={styles.selected}>
        Filtre: {selectedDep === null ? 'Tous les départements' : `Département ${selectedDep}`}
      </Text>
      <FlatList
        data={filteredEtudiants}
        keyExtractor={(item) => item.id.toString()}
        renderItem={({ item }) => (
          <View style={styles.card}>
            <Text style={styles.nom}>{item.nom}</Text>
            <Text>CIN: {item.cin}</Text>
            <Text>Date de naissance: {item.dateNaissance}</Text>
          </View>
        )}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, padding: 16, backgroundColor: '#f5f5f5' },
  center: { flex: 1, justifyContent: 'center', alignItems: 'center' },
  title: { fontSize: 24, fontWeight: 'bold', marginBottom: 16 },
  filters: { flexDirection: 'row', flexWrap: 'wrap', marginBottom: 12 },
  filterBtn: {
    backgroundColor: '#e1e1e1',
    paddingHorizontal: 10,
    paddingVertical: 6,
    borderRadius: 8,
    marginRight: 8,
    marginBottom: 8,
  },
  filterText: { fontWeight: '600' },
  selected: { marginBottom: 8, color: '#444' },
  card: {
    backgroundColor: '#fff',
    padding: 16,
    marginBottom: 8,
    borderRadius: 8,
    elevation: 2,
  },
  nom: { fontSize: 18, fontWeight: 'bold' },
});