type Etudiant = {
  id: number;
  cin: string;
  nom: string;
  dateNaissance: string;
  email: string;
  anneePremiereInscription: number;
  departementNom?: string;
  age: number;
};

async function getEtudiant(id: string): Promise<Etudiant | null> {
  const res = await fetch(`http://api-gateway:8080/api/etudiants/${id}`, { cache: "no-store" });
  if (!res.ok) return null;
  return res.json();
}

export default async function EtudiantDetailPage({ params }: { params: Promise<{ id: string }> }) {
  const { id } = await params;
  const etudiant = await getEtudiant(id);

  if (!etudiant) return <p>Etudiant introuvable</p>;

  return (
    <div>
      <h2>Detail etudiant #{etudiant.id}</h2>
      <p>Nom: {etudiant.nom}</p>
      <p>CIN: {etudiant.cin}</p>
      <p>Email: {etudiant.email}</p>
      <p>Date de naissance: {etudiant.dateNaissance}</p>
      <p>Age: {etudiant.age}</p>
      <p>Annee premiere inscription: {etudiant.anneePremiereInscription}</p>
      <p>Departement: {etudiant.departementNom ?? "Sans departement"}</p>
    </div>
  );
}

