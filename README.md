# zip code range API

Você encontrará a documentação de todos endpoints em http://localhost:8080/swagger-ui/index.html#/

### Tecnologias utilizadas
- Java com Spring
- Spring Data JPA
- Spring Validation
- Banco de dados MySql
- Model Mapper
- Lombok
- SpringDoc (documentação SWAGGER)

### Requisitos do teste:
- Criar API REST para cadastrar as faixas de CEP de cada loja;
- Criar API REST para retornar qual é a loja física que atende determinado CEP;

### Regras
- A tabela de cadastro de CEP deve possuir as seguintes colunas: 
  ID, CODIGO_LOJA, FAIXA_INICIO e FAIXA_FIM;
- As faixas de CEP não podem conflitar com as de outras lojas;
- Deve ser possível editar/excluir uma faixa de CEP;
- A aplicação deve ser feita em Springboot + REST + JPA/Hibernate
