type Etudiant = {
  id: number;
  cin: string;
  nom: string;
  email: string;
  departementNom?: string;
};

async function getEtudiants(): Promise<Etudiant[]> {
  const res = await fetch("http://api-gateway:8080/api/etudiants", { cache: "no-store" });
  if (!res.ok) return [];
  return res.json();
}

export default async function EtudiantsPage() {
  const etudiants = await getEtudiants();

  return (
    <div>
      <h2>Liste des etudiants</h2>
      <ul>
        {etudiants.map((e) => (
          <li key={e.id}>
            <a href={`/etudiants/${e.id}`}>{e.nom}</a> - CIN: {e.cin} - {e.email} -{" "}
            {e.departementNom ?? "Sans departement"}
          </li>
        ))}
      </ul>
    </div>
  );
}

