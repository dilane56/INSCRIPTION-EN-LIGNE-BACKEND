# Synthèse des fonctionnalités backend attendues et état d'implémentation

| Fonctionnalité backend                                 | Présente ? | Commentaire rapide                                                                 |
|--------------------------------------------------------|:----------:|------------------------------------------------------------------------------------|
| Authentification JWT                                   |    OUI     | Filtres JWT, gestion des tokens dans SecurityConfig                                 |
| Authentification OAuth2 (Google, Microsoft, etc.)      |    OUI     | CustomOAuth2UserService, successHandler, endpoints OAuth2 configurés                |
| Audit des connexions                                   |    À VÉRIF | Non visible dans SecurityConfig, à vérifier dans les services/logs                  |
| Gestion des utilisateurs/candidats                     |    OUI     | Endpoints /api/candidats, services associés                                         |
| Processus d’inscription 5 étapes                       |    OUI     | Endpoints, contextes d’inscription, gestion des étapes dans le backend              |
| Upload sécurisé de documents                           |    OUI     | Endpoints pour upload, validation, stockage sécurisé                                |
| Validation automatisée (format, OCR, filigrane, fraude)|    PARTIEL | Format : OUI, OCR/filigrane/fraude : À VÉRIF (dépend des services implémentés)      |
| Génération/envoi d’emails transactionnels              |    OUI     | Présence de templates email, services d’envoi, planification (Quartz à vérifier)    |
| Notifications (email, SMS, in-app)                     |    PARTIEL | Email : OUI, SMS/in-app : À VÉRIF                                                  |
| Tableau de bord analytique                             |    OUI     | Endpoints stats, heatmap, alertes (présence à vérifier dans les services admin)     |
| Export de données (Excel/CSV)                          |    À VÉRIF | Non visible dans SecurityConfig, à vérifier dans les endpoints admin                |
| Recours en ligne (rendez-vous, chat)                   |    NON     | Non visible, à implémenter si besoin                                                |
| Sécurité avancée (chiffrement, détection de copies)    |    PARTIEL | Chiffrement à vérifier, détection de copies/alertes à vérifier                      |
| API publique formations, années académiques            |    OUI     | Endpoints /api/formations, /api/annees-academiques                                 |

**Légende** :
- OUI = fonctionnalité présente et visible dans la config/structure
- PARTIEL = partiellement présente, nécessite vérification ou complétion
- À VÉRIF = à vérifier dans le code/service concerné
- NON = non présente, à implémenter

Pour plus de détails sur une fonctionnalité, se référer au code source ou demander une inspection ciblée.

