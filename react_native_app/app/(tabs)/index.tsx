import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  ActivityIndicator,
} from 'react-native';

type Etudiant = {
  id: number;
  cin: string;
  nom: string;
  dateNaissance: string;
};

export default function HomeScreen() {
  const [etudiants, setEtudiants] = useState<Etudiant[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 10.0.2.2 = IP de la machine hôte depuis l'émulateur Android
    // Pour un appareil physique, remplacer par l'IP locale de votre machine
    fetch('http://10.0.2.2:8080/api/etudiants')
      .then((res) => res.json())
      .then((data) => {
        setEtudiants(data);
        setLoading(false);
      })
      .catch((err) => console.error(err));
  }, []);

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
      <FlatList
        data={etudiants}
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
  card: {
    backgroundColor: '#fff',
    padding: 16,
    marginBottom: 8,
    borderRadius: 8,
    elevation: 2,
  },
  nom: { fontSize: 18, fontWeight: 'bold' },
});