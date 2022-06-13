alunos: Felipe Vilas Boas Marprates e Helen Sousa.
 Todas as cifragens e decifragens foram feitas na classe cripto.
Você implementou todos os requisitos? Houve alguma operação mais difícil? Você enfrentou algum desafio na implementação? Os resultados foram alcançados? ... A ideia, portanto, é relatar como foi a experiência de desenvolvimento do TP. Aqui, a ideia é entender como foi para você desenvolver este TP.
 
a operação mais difícil de ser implementada foi a cifragem de colunas pois eu tive que criar a matriz e depois pensar como mudar a ordem das colunas e eu acabei criando um método de  instruções que comparava os dígitos da chave com a eles em ordem e partir disso vai  criar uma array que retorna as instruções de onde a coluna X deve estar para cifragem e depois eu tive que fazer o contrário. O xor eu achei que seria a mais difícil de implementar mais foi bem fácil ainda mais que o jeito de decifrar é o mesmo de cifrar. Os resultados foram alcançados, mas se eu fizesse de novo eu criaria formas de usar chaves maiores pois do jeito que está implementado não pode ter dígitos repetidos.
Descreva cada uma das operações usadas nas funções de cifragem e de decifragem, explicando como elas embaralham os dados e como usam a chave criptográfica.
a primeira cifragem é uma cifragem de vigenere que criar uma matriz de 256*10 que pega o valor do byte+128 e do valor do dígito da chave sendo usado no momento para achar na matriz o valor correspondente. Depois que eu estava revendo os vídeos de sobre cifragem de vigenere se eu simplesmente tivesse somado o valor do dígito da chave com o do byte teria dado no mesmo resultado. Então a implementação da cifragem foi feita de forma mais simples por meio do número do byte menos o dígito da chave.
na segunda cifragem foi feito uma cifragem de colunas primeiro eu crio uma matriz e depois transformo a chave que é um inteiro para facilitar pegar os dígitos individuais da chave depois eu chamo o metodo getInstrucoes que vai pegar a chave colocar cada um dos dígitos numa array, então ela copia essa array e organiza ela. então ela pesquisa dentro dessa array cada um elementos então se o dígito 9 está na primeira posição mas o resultado da pesquisa retorna que ele no array organizado estaria na última posição o index da última posição é armazenado numa array instruções. Então é criado uma nova matriz que coloca os elementos da entrada de bytes só que na ordem das instruções e não no padrão de  (0,1,2,3...). E finalmente ele transforma a matriz em uma array de byte e retorna. A decifragem é quase a mesma coisa só que as instruções são o contrario entao pesquisa onde o número organizado teria que estar na lista de cifragem ou seja o 9 pra primeira posição
A terceira cifragem é um xor com a chave, então primeiro transforma a chave  e a entrada em uma matriz de boolean para representar uma array de bits, depois é chamado um método xor que percorre pelas duas matrizes subsistindo pelo resultado do xor. E finalmente transforma essa matriz de volta em uma lista de bytes.

 
 
Há uma função de cifragem em todas as classes de entidades, envolvendo pelo menos três operações diferentes e usando uma chave criptográfica?
sim
Há uma função de cifragem correspondente?
sim
O trabalho está funcionando corretamente?
sim
O trabalho está completo?
sim
O trabalho é original e não a cópia de um trabalho de um colega?
sim

