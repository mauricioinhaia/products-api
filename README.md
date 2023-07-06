# Products API CRUD
# 🚧 Sobre o Projeto

Projeto de um CRUD simples para cadastro, manutenção e visualização de dados de produtos. Neste projeto o principal objetivo foi seguir o modelo de Maturidade de Leonard Richardson, que é definido em 4 níveis.
Desses níveis o maior aprendizado que tive foi referente ao nível 3, onde implementamos o recurso de HATEOAS, neste caso a API fornece links para navegar entre seus recursos como, por exemplo, no metodo GET All, fornecemos um link de acesso aos detalhes de apenas um produto, criando dessa maneira uma navegabilidade entre os recursos.

# 📋 Requisitos

- Java (JDK 17)
- PostgreSQL (DB product-api)

# 💻 Como executar o Projeto
```
# Criar uma pasta para salvar o projeto
mkdir /home/seunomeusuario/git

# Acessar a pasta para salvar
cd /home/seunomeusuario/git

# Clonar repositório
git clone https://github.com/mauricioinhaia/products-api.git

# Acessar pasta para executar
cd products-api/

# Executar o Projeto
./mvnw spring-boot:run
```

# Autor
[Mauricio Inhaia](https://www.linkedin.com/feed/ "LinkedIn")