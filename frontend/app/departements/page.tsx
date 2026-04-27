type Departement = {
  id: number;
  nom: string;
};

async function getDepartements(): Promise<Departement[]> {
  const res = await fetch("http://api-gateway:8080/api/departements", { cache: "no-store" });
  if (!res.ok) return [];
  return res.json();
}

export default async function DepartementsPage() {
  const departements = await getDepartements();

  return (
    <div>
      <h2>Liste des departements</h2>
      <ul>
        {departements.map((d) => (
          <li key={d.id}>
            {d.id} - {d.nom}
          </li>
        ))}
      </ul>
    </div>
  );
}

