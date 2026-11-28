
pipeline {

    // Choisir un agent pour executer le pipeline
    agent any

    // Declarer les outils necessaires
    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    // Variables utilisees dans tout le pipeline
    environment {
        DOCKER_IMAGE = "safagr/backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    // Contient toutes les etapes du pipeline
    stages {

        // 1. Recuperer le code depuis GitHub
        stage('1. Checkout Git') {
            steps {
                script {
                    // Adresse du depot GitHub
                    def ghHost = "github.com"
                    def ghPath = "guerchisafa-star/devops_validation.git"

                    // Recuperer le code de la branche backend
                    git branch: 'backend',
                        url: "https://${ghHost}/${ghPath}"
                }
            }
        }

        // 2. Compiler le projet et executer les tests
        stage('2. Build Maven & Tests') {
            steps {
                // Creer le fichier JAR apres compilation et tests
                sh 'mvn clean package'
            }
        }

        // 3. Analyser la qualite du code avec SonarQube
        stage('3. SonarQube Analysis') {
            steps {

                // Recuperer le token SonarQube depuis Jenkins
                withCredentials([
                    string(
                        credentialsId: 'sonar-token',
                        variable: 'SONAR_TOKEN'
                    )
                ]) {
                    sh '''
                        mvn org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                          -Dsonar.projectKey=devops-backend \
                          -Dsonar.projectName=devops-backend \
                          -Dsonar.host.url=http://localhost:9000 \
                          -Dsonar.token=$SONAR_TOKEN
                    '''
                }
            }
        }

        // 4. Construire l'image Docker .
        stage('4. Build Docker Image') {
            steps {
                // Creer l'image avec deux tags : numero du build et latest
                sh 'docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest .'
            }
        }

        // 5. Envoyer l'image vers Docker Hub
        stage('5. Push Docker Hub') {
            steps {

                // Recuperer les identifiants Docker Hub depuis Jenkins
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {

                    // Se connecter a Docker Hub
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

                    // Envoyer les images vers Docker Hub
                    sh 'docker push ${DOCKER_IMAGE}:${IMAGE_TAG}'
                    sh 'docker push ${DOCKER_IMAGE}:latest'
                }
            }
        }

        // 6. Deployer l'application sur Kubernetes
        stage('6. Deploy to Kubernetes') {
            steps {

                // Deployer la base de donnees PostgreSQL
                sh 'kubectl apply -f k8s/postgres.yaml'

                // Deployer l'application Backend
                sh 'kubectl apply -f k8s/deployment.yaml'

                // Redemarrer les Pods du Backend
                sh 'kubectl rollout restart deployment/backend-deployment'
            }
        }
    }
}
