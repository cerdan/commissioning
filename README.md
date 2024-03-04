# Commissioning Control
An app to track the commissioning progress and any problems encountered.


## ENTREGA 01

- [x] Uso de elementos Textview; 
- [x] Uso de elementos EditText (pelo menos 1);
- [x] Uso de elementos RadioButton (pelo menos 2) com pelo menos um RadioGroup; 
- [x] Uso de elementos CheckBox (pelo menos 1);
- [x] Uso de elementos Spinner (pelo menos 1);
- [x] Uso de 2 elementos Button;
  - [x] Um dos Buttons deve ter o rótulo "Limpar" e ao ser clicado limpará os valores dos elementos EditText e desmarcará os RadioButtons e CheckBox, e depois será mostrado um Toast indicando a ação realizada;
  - [x] Um dos Buttons deve ter o rótulo "Salvar" e ao ser clicado irá pegar os valores dos elementos EditText, CheckBox, Spinner e o RadioButton selecionado, e validar estes valores. Caso algum EditText esteja vazio ou nenhum RadioButton selecionado, deverá ser mostrado uma mensagem de erro em um Toast e o foco de edição voltará para o campo vazio (caso seja possível).




## ENTREGA 02

- [x] Criar uma classe de Entidade relacionada ao Tema do Projeto (Pelo menos 4 atributos);
- [x] Carregar de Arrays do Resource um conjunto de dados (pelos menos 4 tipos de informação) que possibilite a instanciação de objetos da Entidade (Pelo menos 10);
- [x] Armazenar as Entidades instanciadas em um ArrayList;
- [x] Criar uma Activity que exiba um componente de listagem de itens ocupando toda tela, pode ser uma ListView ou RecyclerView (para a disciplina recomendo ListView);
- [x] Criar um Adapter customizado para exibir os dados de cada Entidade na listagem de Itens;
  - [x] Ao clicar em um item deve-se mostrar uma mensagem em um Toast indicando que o mesmo foi clicado. A mensagem deve conter informações que identifiquem o elemento;
- [x] A Activity criada de listagem deve ser a principal do Aplicativo (Launcher), para tal no AndroidManifest.xml ela terá mapeada a ação de MAIN e a categoria LAUNCHER. 




## ENTREGA 03

- [x] Crie uma Activity que exibirá os dados de Autoria do App, são eles: Nome do aluno, curso e e-mail, breve descrição do que faz o aplicativo, logo e nome da UTFPR;
- [x] Altere a Activity de Listagem de dados (Feito na Entrega 2), que agora não irá carregar dados de Arrays do Resource, e sim exibir dados cadastrados pela Activity de Cadastro. Para tal coloque no layout:
- [x] Button com rótulo Adicionar, que ao ser clicado abrirá a Activity de cadastro esperando um resultado (startActivityForResult);
- [x] Button com rótulo Sobre, que ao ser clicado abrirá a Activity de Autoria do App (startActivity). 
- [x] Na Activity de Cadastro (Feito na Entrega 1):
- [x] Ao clicar no Button "Salvar" deverá ser recuperado os dados da interface, validados e devolvidos a Activity de Listagem com o método setResult e resultado RESULT_OK.
- [x] Na Activity de Listagem trate o retorno da Activity de Cadastro dentro do método onActivityResult. Neste método receba os valores retornados, crie um objeto da entidade (Feita na Entrega 2), adicione ao ArrayList relacionado ao Adapter customizado, e por fim chame o método notifyDataSetChanged() do Adapter que forçará o redesenho dos itens dentro da ListView ou RecyclerView.


## ENTREGA 04

- [x] Altere a Activity de Listagem:
  - [x] Um MenuItem com o rótulo "Adicionar", um ícone relacionado a esta ação, e com o parâmetro showAsAction com o valor ifRoom; Ao ser clicado deve-se abrir a Activity de cadastro esperando um resultado;
  - [x] Um MenuItem com o rótulo "Sobre", sem ícone, que ao ser clicado abrirá a Activity de Autoria do App (startActivity). 
  - [x] Incluindo um Menu de Ação Contextual que será aberto quando o usuário manter pressionado um dos itens exibidos (na ListView ou RecyclerView). Este menu deve conter:
    - [x] um MenuItem com o rótulo "Editar", um ícone relacionado a esta ação, e com o parâmetro showAsAction com o valor ifRoom; Ao ser acionado deve-se abrir a Activity de Cadastro passando os dados do Item selecionado (para que o usuário possa alterá-los) e esperando um resultado dela;
    - [x] um MenuItem com o rótulo "Excluir", um ícone relacionado a esta ação, e com o parâmetro showAsAction com o valor ifRoom; Ao ser clicado deve-se remover o Item do ArrayList e na sequência chamar o método notifyDataSetChanged() do Adapter, que forçará o redesenho dos itens dentro da ListView ou RecyclerView.
    - [x] modificando o método onActivityResult para que quando se retorne da Activity de Cadastro com sucesso (RESULT_OK) e com os novos valores de um item que sofreu edição, estes possam ser recuperados e atribuídos ao objeto da Entidade correspondente. Não esquecendo de após alterar as Entidades do ArrayList chamar o método notifyDataSetChanged() do Adapter, que forçará o redesenho dos itens dentro da ListView ou RecyclerView.
- [x] Altere a Activity de Cadastro:
  - [x] permitindo que a mesma seja aberta em modo de edição, onde ela já é aberta com dados de uma Entidade já cadastrada, e o usuário poderá alterar os atributos preenchidos;
  - [x] retirando os Buttons e incluindo um menu de opções com:
    - [x] Um MenuItem com o rótulo "Salvar, um ícone relacionado a esta ação, e com o parâmetro showAsAction com o valor ifRoom; Ao ser clicado deve-se recuperar os dados da interface, validá-los e devolvê-los para a Activity de Listagem com o método setResult e resultado RESULT_OK;
    - [x] Um MenuItem com o rótulo "Limpar, um ícone relacionado a esta ação, e com o parâmetro showAsAction com o valor ifRoom. Ao ser clicado deve-se limpar os valores cadastrados (EditText, CheckBox e RadioButtons) e mostrar uma mensagem em um Toast indicando a ação realizada.
  - [x] incluindo um botão Up na barra do App, que quando clicado retorna para a Activity de Listagem cancelando a inclusão ou edição de dados aberta.
- [x] Altere a Activity com informações sobre a Autoria do Aplicativo:
  - [x] incluindo um botão Up na barra do App, que quando clicado retorna para a Activity de Listagem.




## ENTREGA 05

- [x] Internacionalizar o aplicativo incorporando suporte a dois idiomas, o Inglês geral como padrão e o português do Brasil como opcional.
  - [x] Todos os textos fixos de interface devem ter as duas opções de tradução, sejam os apresentados na Activity ou os mostrados em janelas modais ou em caixas de mensagens (como Toast).
- [x] Incorporar alguma funcionalidade de configuração/personalização do aplicativo por parte do usuário, sendo que as escolhas feitas por ele serão persistidas no dispositivo através do uso de SharedPreferences. Exemplos de possíveis configurações:
  - [x] Escolher forma de ordenação de itens em uma lista;
  - [x] Habilitar ou Desabilitar se campos do cadastro já aparecerão com sugestão de preenchimento; 
  - [x] Definir se o aplicativo será apresentado no Modo Normal ou no Noturno;
  - [x] Mostrar as funcionalidades mais usadas em destaque.
- [x] Não serão aceitos trocar a cor de fundo de layout (exemplo passado), ou salvar login e senha de usuário (visto que o aplicativo é para uso sem conexão).




## ENTREGA 06




## PROJETO FINAL

- [x] Especifique o tema que trata a aplicação. Este tema deverá ter sido lançado pelo aluno no questionário "Tema do Projeto", e o mesmo precisa ser aprovado previamente pelo professor. Não serão aceitos temas repetidos dentro da turma.
- [ ] Cadastre dados lançados pelo usuário, que tenham relação com a regra de negócio proposta para a aplicação (Nesta versão os dados serão persistidos no SQLite através do uso do Room);
- [ ] Crie pelo menos uma Classe de Entidade a ser manipulada dentro da aplicação;
- [ ] Crie pelo menos uma Activity que permita a manipulação dos dados (Inserção, Alteração e Remoção);
- [ ] Exiba um AlertDialog para confirmar a ação do usuário antes de excluir dados persistidos; 
- [x] Crie pelo menos uma Activity que liste os itens cadastrados no banco de dados;
- [x] Utilize pelo menos um Adapter Customizado em uma Activity que liste os itens;
- [x] Crie uma Activity onde mostra-se as informações sobre o que faz o aplicativo, e os dados da autoria dele;
- [x] Utilize Menus de Opções, onde as ações aparecem com ícones na barra de ação da Activity;
- [x] Utilize Menu de Ação Contextual, onde as ações aparecem com ícones na barra de ação da Activity;
- [ ] Utilize botões Up na barra de ações das Activities secundárias, para facilitar a volta do usuário destas para as Activities especificadas como pais destas;
- [x] Forneça alguma possibilidade de configuração ou personalização do aplicativo, e persista estas informações usando SharedPreferences; 
- [x] O Aplicativo deve suportar dois idiomas, o inglês como padrão e o português Brasileiro como opcional.

