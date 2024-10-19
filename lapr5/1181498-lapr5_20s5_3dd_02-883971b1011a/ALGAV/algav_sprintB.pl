:-dynamic no/6.
:-dynamic linha/5.
:-dynamic horario/2.

no('Aguiar de Sousa', 'AGUIA', t, f, -8.4464785432391, 41.1293363229325).
no('Baltar', 'BALTR', t, f, -8.38716802227697, 41.1937898023744).
no('Besteiros', 'BESTR', t, f, -8.34043029659082, 41.217018845589).
no('Cete', 'CETE', t, f, -8.35164059584564, 41.183243425797).
no('Cristelo', 'CRIST', t, f, -8.34639896125324, 41.2207801252676).
no('Duas Igrejas', 'DIGRJ', t, f, -8.35481024956726, 41.2278665802794).
no('Estação (Lordelo)', 'ESTLO', f, t, -8.4227924957086, 41.2521157104055).
no('Estação (Paredes)', 'ESTPA', f, t, -8.33448520831829, 41.2082119860192).
no('Gandra', 'GAND', t, f, -8.43958765792976, 41.1956579348384).
no('Lordelo', 'LORDL', t, f, -8.42293614720057, 41.2452627470645).
no('Mouriz', 'MOURZ', t, f, -8.36577272258403, 41.1983610215263).
no('Parada de Todeia', 'PARAD', t, f, -8.37023578802149, 41.1765780321068).
no('Paredes', 'PARED', t, f, -8.33566951069481, 41.2062947118362).
no('Recarei', 'RECAR', t, f, -8.42215867462191, 41.1599363478137).
no('Sobrosa', 'SOBRO', t, f, -8.38118071581788, 41.2557331783506).
no('Vandama', 'VANDO', t, f, -8.34160692293342, 41.2328015719913).
no('Vila Cova de Carros', 'VCCAR', t, f, -8.35109395257277, 41.2090666564063).

linha('Paredes_Aguiar', 1, ['AGUIA','RECAR', 'PARAD', 'CETE', 'PARED'], 31, 15700).
linha('Paredes_Aguiar', 3, ['PARED', 'CETE','PARAD', 'RECAR', 'AGUIA'], 31, 15700).
linha('Paredes_Gandra', 5 , ['GAND', 'VANDO', 'BALTR', 'MOURZ', 'PARED'], 26, 13000).
linha('Paredes_Gandra', 8, ['PARED', 'MOURZ', 'BALTR', 'VANDO', 'GAND'], 26, 13000).
linha('Paredes_Lordelo', 9, ['LORDL','VANDO', 'BALTR', 'MOURZ', 'PARED'], 29, 14300).
linha('Paredes_Lordelo', 11, ['PARED','MOURZ', 'BALTR', 'VANDO', 'LORDL'], 29, 14300).
linha('Lordelo_Parada', 24, ['LORDL', 'DIGRJ', 'CRIST', 'VCCAR', 'BALTR', 'PARAD'], 22, 11000).
linha('Lordelo_Parada', 26, ['PARAD', 'BARTR', 'VCCAR', 'CRIST', 'DIGRJ', 'LORDL'], 22, 11000).
%linha('Cristelo_Baltar', nd0, ['CRIST', 'VCCAR', 'BALTR'], 8, 4000).
%linha('Baltar_Cristelo', nd1, ['BARTR', 'VCCAR', 'CRIST'], 8, 4000).
linha('Sobrosa_Cete', 22, ['SOBRO', 'CRIST', 'BESTR', 'VCCAR', 'MOURZ', 'CETE'], 23, 11500).
linha('Sobrosa_Cete', 20, ['CETE', 'MOURZ', 'VCCAR', 'BESTR', 'CRIST', 'SOBRO'], 23, 11500).
linha('Estação(Lordelo)_Lordelo',34,['ESTLO','LORDL'], 2,1500).
linha('Lordelo_Estação(Lordelo)',35,['LORDL','ESTLO'], 2,1500).
linha('Estação(Lordelo)_Sobrosa',36,['ESTLO','SOBRO'], 5,1500).
linha('Sobrosa_Estação(Lordelo)',37,['SOBRO','ESTLO'], 5,1800).
linha('Estação(Paredes)_Paredes',38,['ESTPA','PARED'], 2,1500).
linha('Paredes_Estação(Paredes)',39,['PARED','ESTPA'], 2,1500).
%linha('CRIST_ESTLO', 40, ['CRIST','A1','ESTLO'], 1, 1).
%linha('ESTPA_A1', 41, ['ESTPA', 'PARED', 'SOBRO', 'A1'], 1, 1).
%linha('A1_A2', 42, ['A1','A2'], 2, 1500).
%linha('PARED_A1', 43, ['PARED', 'LORDL', 'A2', 'A1'], 1, 1).
%linha('ESTPA_CRIST', 44, ['ESTPA','A3','CRIST'], 1, 1).
%linha('A2_ESTLO', 45, ['A2', 'A3', 'ESTLO'], 1, 1).
%linha('PARED_A1', 46, ['PARED', 'LORDL', 'A3', 'A1'], 1, 1).
%linha('ESTPA_A3', 47, ['ESTPA','A4','A2','CRIST','A3'], 1, 1).
%linha('PARED_A1', 48, ['PARED','A4','A1'], 1, 1).
%adição de linhas para testes e estudo da viabilidade dos horários
%linha('PARED_BALTR', 49, ['PARED','CETE','A1','BALTR'], 8, 4000).
%linha('PARED_RECAR', 50, ['PARED','MOURZ','A1','PARAD','RECAR'], 8, 4000).
%linha('A1_SOBRO', 51, ['A1','CRIST','SOBRO'], 8, 4000).
%linha('A1_DIGRJ', 52, ['A1','DIGRJ'], 8, 4000).
%linha('A2_VCCAR', 53, ['A2','BALTR','VCCAR'], 8, 4000).
%linha('A2_RECAR', 53, ['A2','PARAD','RECAR'], 8 4000).

horario(3,[73800,74280,74580,74880,75420]).
horario(3,[72900,73380,73680,73980,74520]).
horario(3,[72000,72480,72780,73080,73620]).
horario(3,[71100,71580,71880,72180,72720]).
horario(3,[70200,70680,70980,71280,71820]).
horario(3,[69300,69780,70080,70380,70920]).
horario(1,[54000,54540,54840,55140,55620]).
horario(1,[55800,56340,56640,56940,57420]).
horario(1,[57600,58140,58440,58740,59220]).
horario(11,[49020,49500,49740,49980,50760]).
horario(11,[50820,51540,51540,51780,52560]).
horario(11,[47220,47700,47940,48180,48960]).
horario(9,[50760,51540,51780,52020,52500]).
horario(9,[52560,53340,53580,53820,54300]).
horario(9,[54360,55140,55380,55620,56100]).
horario(9,[47220,47700,47940,48180,48960]).
horario(38,[30000,30120]).
horario(38,[67800,67800]).
horario(38,[27300,27420]).
%horario(35,[75840,75960]).
%horario(35,[76320,76440]).
horario(24,[49140,49440,49680,49920,50160,50460]).
horario(24,[43860,44160,44400,44640,44880,45180]).
horario(20,[50760,51060,51300,51540,51780,52028]).
horario(20,[57360,57660,57900,58140,58380,58680]).
horario(37,[77160,77460]).
%adição de horários para testes e estudo da viabilidade
%horario(26,[71880,72180,72420,72660,72900,73200]).
%horario(26,[70560,70860,71100,71340,71580,71880]).
%horario(20,[72180,72480,72720,72960,73320,73560]).
%horario(20,[28980,29280,29520,29760,30120,73560]).
%horario(8,[32400,32880,33120,33360]).
%horario(22,[28800,29040,29400,29640,29880,30180]).
%horario(5,[30360,30960,31200,31440,31920]).
%horario(5,[37560,38160,38400,38640,39120]).
%horario(8,[32400,32880,33120,33360,33960]).
%horario(8,[28860,29340,29580,29820,30360]).
%horario(26,[71880,72180,72420,72660,72900,73200]).
%horario(26,[33600,33900,34140,34380,34620,34920]).
%horario(49,[27430,28200,28600,28800]).
%horario(50,[27430,27740,27950,28400,29300]).
%horario(51,[28610,28700,29000]).
%horario(52,[28610,29000]).
%horario(42,[28605,28610]).
%horario(53,[28620,28800,29400]).
%horario(54,[28620,29000,29500]).


% gerar todas as ligações diretas entre pontos de rendição ou estações de recolha
:-dynamic liga/3.
gera_ligacoes:- retractall(liga(_,_,_)),
	findall(_,
		((no(_,No1,t,f,_,_);no(_,No1,f,t,_,_)),
		(no(_,No2,t,f,_,_);no(_,No2,f,t,_,_)),
		No1\==No2,
		linha(_,N,LNos,_,_),
		ordem_membros(No1,No2,LNos),
		assertz(liga(No1,No2,N))
		),_).

ordem_membros(No1,No2,[No1|L]):- member(No2,L),!.
ordem_membros(No1,No2,[_|L]):- ordem_membros(No1,No2,L).

% gerar um caminho entre um ponto de rendição ou ponto de recolha para outro ponto de rendição ou ponto de recolha e envolvendo o uso de uma ou mais linhas de autocarro
caminho(Noi,Nof,LCaminho):-caminho(Noi,Nof,[],LCaminho).

caminho(No,No,Lusadas,Lfinal):-reverse(Lusadas,Lfinal).
caminho(No1,Nof,Lusadas,Lfinal):-
	liga(No1,No2,N),
	\+member((_,_,N),Lusadas),
	\+member((No2,_,_),Lusadas),
	\+member((_,No2,_),Lusadas),
	caminho(No2,Nof,[(No1,No2,N)|Lusadas],Lfinal).
	
% gerar o caminho entre um ponto de rendição ou ponto de recolha para outro ponto de rendição ou ponto de recolha envolvendo o uso do menor número de linhas
menor_ntrocas(Noi,Nof,LCaminho_menostrocas):-
	findall(LCaminho,caminho(Noi,Nof,LCaminho),LLCaminho),
	menor(LLCaminho,LCaminho_menostrocas).

menor([H],H):-!.
menor([H|T],Hmenor):-menor(T,L1),length(H,C),length(L1,C1),
	((C<C1,!,Hmenor=H);Hmenor=L1).
	
% análise da viabilidade e complexidade do gerador de soluções
plan_mud_mot(Noi,Nof,LCaminho_menostrocas):-
	get_time(Ti),
	findall(LCaminho,caminho(Noi,Nof,LCaminho),LLCaminho),
	menor(LLCaminho,LCaminho_menostrocas),
	get_time(Tf),
	length(LLCaminho,NSol),
	TSol is Tf-Ti,
	write('Numero de Solucoes:'),write(NSol),nl,
	write('Tempo de geracao da solucao:'),write(TSol),nl.
	
% análise da viabilidade e complexidade das soluções sem findall
:- dynamic melhor_sol_ntrocas/2.
plan_mud_mot1(Noi,Nof,LCaminho_menostrocas):-
	get_time(Ti),
	(melhor_caminho(Noi,Nof);true),
	retract(melhor_sol_ntrocas(LCaminho_menostrocas,_)),
	get_time(Tf),
	TSol is Tf-Ti,
	write('Tempo de geracao da solucao:'),write(TSol),nl.	
	
melhor_caminho(Noi,Nof):-
	asserta(melhor_sol_ntrocas(_,10000)),
	caminho(Noi,Nof,LCaminho),
	atualiza_melhor(LCaminho),
	fail.
	
atualiza_melhor(LCaminho):-
	melhor_sol_ntrocas(_,N),
	length(LCaminho,C),
	C<N,retract(melhor_sol_ntrocas(_,_)),
	asserta(melhor_sol_ntrocas(LCaminho,C)).
	
	
% adaptação do gerador sem findall à minimização de horários
plan_mud_mot2(Noi,Nof,Hi,LCaminho_menostempo,Custo):-
	get_time(Ti),
	(melhor_caminho_h(Noi,Nof,Hi);true),
	retract(melhor_horario(LCaminho_menostempo,_)),
	calcular_horario(LCaminho_menostempo,Hi,0,Custo), !,
	get_time(Tf),
	TSol is Tf-Ti,
	write('Tempo de geracao da solucao:'),write(TSol),nl.

:- dynamic melhor_horario/2.
melhor_caminho_h(Noi,Nof,Hi):-
	asserta(melhor_horario(_,100000)),
	caminho(Noi,Nof,LCaminho),
	atualiza_melhor_h(LCaminho,Hi),
	fail.
	
atualiza_melhor_h(LCaminho,Hi):-
	melhor_horario(_,N),
	calcular_horario(LCaminho,Hi,0,C),
	write('Caminho: '), write(LCaminho), nl,
	write('Tempo: '), write(C), nl,
	C<N,retract(melhor_horario(_,_)),
	asserta(melhor_horario(LCaminho,C)).
	
calcular_horario([],_,N,N).
calcular_horario([T|H],Ha,N,C):-
	T = (A,B,P),
	findall(Horarios,horario(P,Horarios),LHorario),
	sort(LHorario,SLHorario),
	linha(_, P, LLinha, _, _),
	nth1(Noi,LLinha,A),
	nth1(Nof,LLinha,B),
	find_horario(SLHorario,Ha,Noi,LHorario_int),
	nth1(Noi,LHorario_int,TNoi),
	nth1(Nof,LHorario_int,TNof),
	TEspera is TNoi - Ha,
	TViagem is TNof - TNoi,
	C1 is TEspera + TViagem,
	N1 is N + C1,
	calcular_horario(H,TNof,N1,C).
	
find_horario([],_,_,_) :- fail.
find_horario([T|H],Ha,Noi,LHorario_int):-
	nth1(Noi,T,TParagem),
	TParagem > Ha,
	LHorario_int = T, !;
	find_horario(H,Ha,Noi,LHorario_int).
	
	
% A-Star
aStar(Orig,Dest,Hi,Cam,Custo):-
	aStar2(Orig,Dest,[(_,0,[])],Hi,Cam,CustoS), Custo is CustoS - Hi.
aStar2(_,Dest,[(_,Custo,[(Act,Dest,List)|T])|_],_,Cam,Custo):-
	reverse([(Act,Dest,List)|T],Cam).
aStar2(Act,Dest,[(_,Ca,LA)|Outros],Hi,Cam,Custo):-
	findall((CEX,CaX,[(Act,X,Line)|LA]),
		(Dest\==Act, liga(Act,X,Line),
		\+member((_,_,Line),LA),
		\+member((_,X,_),LA),
		\+member((X,_,_),LA),
		calcular_horario([(Act,X,Line)],Hi,0,T),
		CaX is T + Ca, estimativa(X,Dest,EstX),
		CEX is CaX + EstX),Novos),
	append(Outros,Novos,Todos),
	%write('Novos: '), write(Novos), nl,
	sort(Todos,TodosOrd),
	%write('TodosOrd: '), write(TodosOrd), nl,
	nth1(1,TodosOrd,GTv),
	GTv = (_,Ha,L),
	nth1(1,L,GHa2),
	GHa2 = (_,Ha2,_),
	aStar2(Ha2,Dest,TodosOrd,Ha,Cam,Custo).

estimativa(Nodo1,Nodo2,Estimativa):-
	no(_,Nodo1,_,_,Long1,Lat1),
	no(_,Nodo2,_,_,Long2,Lat2),
	calcular_distancia(Lat1,Long1,Lat2,Long2,D),
	calcular_velocidade_media(V),
	Estimativa is D/V.
	
calcular_distancia(Lat1,Long1,Lat2,Long2,D):-
	R is 6371000,
	RLat1 is Lat1 * pi/180,
	RLat2 is Lat2 * pi/180,
	DLat is (Lat2 - Lat1) * pi/180,
	DLong is (Long2 - Long1) * pi/180,
	pow(sin(DLat/2),2,L1),
	pow(sin(DLong/2),2,L2),
	A is L1 + cos(RLat1) * cos(RLat2) * L2,
	C is 2 * atan2(sqrt(A), sqrt(1-A)),
	D is R * C.

calcular_velocidade_media(V):-
	findall((T,D),linha(_,_,_,T,D),LTempoDist),
	get_velocidade_maxima(LTempoDist,0,V).
	
get_velocidade_maxima([],Vi,Vi).
get_velocidade_maxima([T|H],Vi,V):-
	T = (Temp,Dist),
	Vel is Dist/(Temp*60),
	Vel > Vi,!, get_velocidade_maxima(H,Vel,V);get_velocidade_maxima(H,Vi,V). 
	
%/	
% análise da viabilidade e complexidade do gerador de soluções
plan_mud_mot_aStar(Orig,Dest,Hi,Cam,Custo):-
	get_time(Ti),
	aStar(Orig,Dest,Hi,Cam,Custo),!,
	get_time(Tf),
	TSol is Tf-Ti,
	write('Tempo de geracao da solucao:'),write(TSol),nl.


%bestfs(Orig,Dest,Hi,Cam,Custo):-
%	bestfs2(Orig,Dest,Hi,[],Cam),
%	calcular_horario(Cam,Hi,0,Custo), !.

bestfs(Orig,Dest,Hi,Cam,Custo):-
	bestfs2(Orig,Dest,[],Cam),
	calcular_horario(Cam,Hi,0,Custo), !.

bestfs2(_,Dest,_,[(Act,Dest,Line)|T],Cam):- !,
	reverse([(Act,Dest,Line)|T],Cam).
	
bestfs2(_,Dest,[(Act,Dest,Line)|T],Cam):- !,
	reverse([(Act,Dest,Line)|T],Cam).

%bestfs2(Act,Dest,Hi,LA,Cam):-
%	findall((EstX,Ha,[(Act,X,Line)|LA]),
%		(liga(Act,X,Line),
%		\+member((_,_,Line),LA),
%		\+member((_,X,_),LA),
%		\+member((X,_,_),LA),
%		calcular_horario([(Act,X,Line)],Hi,0,T),
%		Ha is Hi + T,
%		estimativa_distancia(X,Dest,EstX)), 
%		Novos),
%	sort(Novos,NovosOrd),
%	NovosOrd = [(_,Ha,Melhor)|_],
%	Melhor = [(_,Orig2,_)|_],
%	bestfs2(Orig2,Dest,Ha,Melhor,Cam).
	
bestfs2(Act,Dest,LA,Cam):-
	findall((EstX,[(Act,X,Line)|LA]),
		(liga(Act,X,Line),
		\+member((_,_,Line),LA),
		\+member((_,X,_),LA),
		\+member((X,_,_),LA),
		estimativa_distancia(X,Dest,EstX)), 
		Novos),
	sort(Novos,NovosOrd),
	NovosOrd = [(_,Melhor)|_],
	Melhor = [(_,Orig2,_)|_],
	bestfs2(Orig2,Dest,Melhor,Cam).

estimativa_distancia(Nodo1,Nodo2,Estimativa):-
	no(_,Nodo1,_,_,Long1,Lat1),
	no(_,Nodo2,_,_,Long2,Lat2),
	calcular_distancia(Lat1,Long1,Lat2,Long2,Estimativa).
	
plan_mud_mot_bestfs(Orig,Dest,Hi,Cam,Custo):-
	get_time(Ti),
	bestfs(Orig,Dest,Hi,Cam,Custo),!,
	get_time(Tf),
	TSol is Tf-Ti,
	write('Tempo de geracao da solucao:'),write(TSol),nl.