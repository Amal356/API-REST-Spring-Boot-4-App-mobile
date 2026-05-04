/// <reference types="cypress" />

describe('Gestion des étudiants', () => {
  beforeEach(() => {
    // Visiter la page des étudiants avant chaque test
    cy.visit('/etudiants')
  })

  it('affiche la liste des étudiants', () => {
    // Vérifier que la page se charge
    cy.contains('Liste des étudiants').should('be.visible')
    
    // Vérifier qu'il y a au moins un étudiant dans la liste
    cy.get('ul li').should('have.length.greaterThan', 0)
    
    // Vérifier que les informations de base sont affichées
    cy.get('ul li').first().should('contain', 'CIN')
  })

  it('affiche les détails d\'un étudiant', () => {
    // Cliquer sur le premier étudiant de la liste
    cy.get('ul li a').first().click()
    
    // Vérifier qu'on est sur la page de détails
    cy.url().should('include', '/etudiants/')
    
    // Vérifier que les informations sont affichées
    cy.contains('CIN').should('be.visible')
    cy.contains('Email').should('be.visible')
  })

  it('navigue vers la page des départements', () => {
    // Cliquer sur le lien départements dans la navigation
    cy.contains('Departements').click()
    
    // Vérifier qu'on est sur la page des départements
    cy.url().should('include', '/departements')
    cy.contains('Liste des departements').should('be.visible')
  })

  it('affiche la page d\'accueil et redirige vers étudiants', () => {
    // Visiter la page d'accueil
    cy.visit('/')
    
    // Vérifier la redirection vers /etudiants
    cy.url().should('include', '/etudiants')
  })
})

describe('Gestion des départements', () => {
  beforeEach(() => {
    cy.visit('/departements')
  })

  it('affiche la liste des départements', () => {
    // Vérifier que la page se charge
    cy.contains('Liste des departements').should('be.visible')
    
    // Vérifier qu'il y a au moins un département
    cy.get('ul li').should('have.length.greaterThan', 0)
    
    // Vérifier que les noms de départements sont affichés
    cy.get('ul li').first().should('contain', '-')
  })

  it('navigue entre les pages', () => {
    // Aller vers étudiants
    cy.contains('Etudiants').click()
    cy.url().should('include', '/etudiants')
    
    // Revenir vers départements
    cy.contains('Departements').click()
    cy.url().should('include', '/departements')
  })
})

describe('Tests de l\'API via le frontend', () => {
  it('charge les données depuis l\'API Gateway', () => {
    // Intercepter les appels API
    cy.intercept('GET', '**/api/etudiants').as('getEtudiants')
    
    // Visiter la page
    cy.visit('/etudiants')
    
    // Attendre que l'API réponde
    cy.wait('@getEtudiants').its('response.statusCode').should('eq', 200)
  })

  it('gère les erreurs API gracieusement', () => {
    // Simuler une erreur API
    cy.intercept('GET', '**/api/etudiants', {
      statusCode: 500,
      body: { error: 'Internal Server Error' }
    }).as('getEtudiantsError')
    
    // Visiter la page
    cy.visit('/etudiants')
    
    // Vérifier que la page ne crash pas
    cy.wait('@getEtudiantsError')
    cy.get('body').should('exist')
  })
})
