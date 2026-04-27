import type { ReactNode } from "react";

export default function RootLayout({ children }: { children: ReactNode }) {
  return (
    <html lang="fr">
      <body style={{ fontFamily: "Arial, sans-serif", margin: 0, padding: 20 }}>
        <h1>Frontend Etudiants (API Gateway)</h1>
        <nav style={{ marginBottom: 16 }}>
          <a href="/etudiants" style={{ marginRight: 12 }}>
            Etudiants
          </a>
          <a href="/departements">Departements</a>
        </nav>
        {children}
      </body>
    </html>
  );
}

