# Build avec tag Docker Hub
docker build -t amal878/etudiant-service:1.0 .

# Push sur Docker Hub
docker push amal878/etudiant-service:1.0

# Instructions pour K3S
kubectl apply -f k8s/
