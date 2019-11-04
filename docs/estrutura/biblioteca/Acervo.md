# Manipulação do Acervo

Lê e escreve sobre "Acervo", "Livros", "Acadêmicos", "Mídias" e "Periódicos".
Lê sobre "Empréstimos", "Reservas" e "Alunos".

## Como utilizar

##### Parâmetros

- **Gerais**: id (int); id-campi (int); nome (string); tipo (string); local (string); ano (int); editora (string); paginas (int); id-obra (int);
- **Acadêmicos**: programa (string);
- **Livros**: edicao (int); isbn (long int);
- **Mídias**: tempo (string no formato 23:59:59); subtipo (um dentre 'CD', 'DVD', 'FITA', 'PENDRIVE');
- **Periódicos**: periodicidade (string); mes (string); volume (int); subtipo (string); issn (int)

#### Inserir

- **Acesso**: {endereço_hospedado}/biblioteca/acervo/inserir

- **Parâmetros obrigatórios**: todos exceto 'id' (não exclui 'id-obra')

- **Paramêtros opcionais**: nenhum

- **Paramêtros ignorados**: 'id'

#### Deletar

- **Acesso**: {endereço_hospedado}/biblioteca/acervo/deletar

- **Parâmetros obrigatórios**: apenas 'id'

- **Paramêtros opcionais**: nenhum

- **Paramêtros ignorados**: todos exceto 'id'

#### Atualizar

- **Acesso**: {endereço_hospedado}/biblioteca/acervo/atualizar

- **Parâmetros obrigatórios**: todos exceto 'id' (não exclui 'id-obra')

- **Paramêtros opcionais**: nenhum

- **Paramêtros ignorados**: 'id'

#### Consultar

- **Acesso**: {endereço_hospedado}/biblioteca/acervo/consultar

- **Parâmetros obrigatórios**: nenhum

- **Paramêtros opcionais**: 'id' faz com que apenas o item de id 'id' seja retornado

- **Paramêtros ignorados**: todos exceto 'id'

## Exemplo XML de consulta

```
<acervo>
	<item>
		<id>2</id>
		<id-campi>0</id-campi>
		<nome>SherlockHolmes</nome>
		<tipo>LIVROS</tipo>
		<local>Londres</local>
		<ano>2014</ano>
		<editora>MartinClaret</editora>
		<paginas>600</paginas>
		<livro>
			<id-obra>1</id-obra>
			<edicao>5</edicao>
			<isbn>9788544000229</isbn>
		</livro>
	</item>
	<item>
		<id>3</id>
		<id-campi>0</id-campi>
		<nome>TheH8ful8</nome>
		<tipo>MIDIAS</tipo>
		<local>US</local>
		<ano>2018</ano>
		<editora>Tarantola</editora>
		<paginas>0</paginas>
		<midia>
			<id-obra>1</id-obra>
			<tempo>23:32:00</tempo>
			<subtipo>PENDRIVE</subtipo>
		</midia>
	</item>
</acervo>
```