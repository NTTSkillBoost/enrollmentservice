# Variáveis
# Nome da imagem Docker e do container
APP_NAME=client-service-api
# Onde está o .jar gerado após o build
JAR_FILE=target/*.jar
# Porta onde a aplicação será exposta
PORT=9093
# Nome do usuário do Docker Hub
DOCKER_USER=rogeriofontes
# Tag da imagem Docker
IMAGE_TAG=latest
# Defina como true para habilitar o push
ENABLE_DOCKER_PUSH=false
# ☁️ Push da imagem Docker para o Docker Hub (somente se habilitado)
TAGGED_IMAGE=$(DOCKER_USER)/$(APP_NAME):$(IMAGE_TAG)

# Alvo padrão
.DEFAULT_GOAL := help # Define o alvo padrão como 'help', se nenhum argumento for passado

# Detecta SO e define o comando Maven apropriado
ifeq ($(OS),Windows_NT) # Se o sistema operacional for Windows
	#MVN_CMD=mvnw.cmd # Comando para Windows
	MVN_CMD=./mvnw
else
	MVN_CMD=./mvnw # Comando para Linux/Mac
endif

# 🔨 Build do projeto
build:
	@echo "🔧 Buildando o projeto..."
	$(MVN_CMD) clean package -DskipTests # Gera o .jar sem rodar os testes

# 🐳 Build da imagem Docker
docker-build: build
	@echo "🐳 Buildando imagem Docker..."
	docker build -t $(APP_NAME) . # Gera a imagem Docker com o nome definido na variável APP_NAME

# ☁️ Push da imagem Docker para o Docker Hub
docker-push:
ifeq ($(ENABLE_DOCKER_PUSH),true)
	@echo "🔐 Fazendo login no Docker Hub..."
	docker login
	@if [ -z "$(DOCKER_USER)" ]; then \
    		echo "❌ Variável DOCKER_USER não definida!"; \
    		exit 1; \
    fi
	@echo "☁️ Enviando imagem para o Docker Hub..."
	docker tag $(APP_NAME) $(TAGGED_IMAGE)
	docker push $(TAGGED_IMAGE)
else
	@echo "⚠️ Push desabilitado. Para habilitar, use: make docker-push ENABLE_DOCKER_PUSH=true"
endif

# 🚀 Subir container com Docker Compose
up:
	@echo "🚀 Subindo os containers..."
	docker-compose up -d # Sobe os containers em segundo plano

# 🛑 Derrubar container
down:
	@echo "🛑 Parando os containers..."
	docker-compose down # Derruba os containers

# 📜 Logs da aplicação
logs:
	@echo "📜 Logs da aplicação..."
	docker logs -f $$(docker ps -qf "name=$(APP_NAME)") # Mostra os logs do container com o nome definido na variável APP_NAME

# 🗑️ Derrubar e remover tudo
clean:
	@echo "🧹 Limpando imagens e containers..."
	docker-compose down --volumes --remove-orphans # Derruba os containers e remove volumes e containers órfãos

# 🆘 Ajuda
help:
	@echo ""
	@echo "💡 Comandos disponíveis:"
	@echo " make build          → Builda o projeto com Maven"
	@echo " make docker-build   → Gera a imagem Docker"
	@echo " make docker-push ENABLE_DOCKER_PUSH=true  → Push para Docker Hub"
	@echo " make up             → Sobe os containers com docker-compose"
	@echo " make down           → Derruba os containers"
	@echo " make logs           → Mostra os logs da aplicação"
	@echo " make clean          → Limpa volumes e containers órfãos"
	@echo ""
