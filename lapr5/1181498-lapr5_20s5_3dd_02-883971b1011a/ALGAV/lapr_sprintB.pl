:-dynamic no/6.
:-dynamic linha/5.
:-dynamic horario/2.
% Bibliotecas HTTP
%:- use_module(library(http/http_unix_daemon)).
:- use_module(library(http/http_open)).
:- use_module(library(http/http_cors)).
:- use_module(library(date)).
:- use_module(library(random)).
:- use_module(library(http/thread_httpd)).
:- use_module(library(http/http_dispatch)).
:- use_module(library(http/http_parameters)).
:- use_module(library(http/http_client)).
% Bibliotecas JSON
:- use_module(library(http/json_convert)).
:- use_module(library(http/http_json)).
:- use_module(library(http/json)).

:- json_object node(nodeId:string, name:string, shortName:string, latitude:number, longitude:number, isDepot:boolean, isReliefPoint:boolean).
:- json_object line(lineId:string, name:string, color:string, pathGo:[string], pathReturn:[string], vehicleType:string, driverType:string).
:- json_object pathLine(goPaths:[json_object(dict)], id:string, returnPaths:[json_object(dict)]).
:- json_object path(key:string, pathId:string, pathNodes:[json_object(dict)]).
:- json_object pathNode('_id':string, distancia:number, duracao:number, finalNode:string, inicialNode:string).
:- json_object horario(path:string, schedule:[number]).
		
getNodes() :-
		http_get('https://api-mdr-lapr5.herokuapp.com/api/nodes/all?order=name', Reply, [json_object(dict)]),
		retractall(no(_,_,_,_,_,_)),
		loop_list_nos(Reply).

loop_list_nos([]).
loop_list_nos([H|T]):-
	getBool(H.isDepot, Dep),
    getBool(H.isReliefPoint, Rel),
	string_to_atom(H.shortName, A),	
    assertz(no(H.name, A, Rel, Dep, H.longitude, H.latitude)),
    loop_list_nos(T).

getBool(true, t).
getBool(false, f).		

getLines() :-
	http_get('https://api-mdr-lapr5.herokuapp.com/api/lines/all?order=name', Reply, [json_object(dict)]),
	retractall(linha(_,_,_,_,_)),
	loop_list_line(Reply).
	
loop_list_line([]).
loop_list_line([H|T]):-
	atom_concat('https://api-mdr-lapr5.herokuapp.com/api/lines/pathfromline?id=', H.lineId, URL),
	http_get(URL, Reply, [json_object(dict)]),
	loop_list_path(Reply.goPaths, H),
	loop_list_path(Reply.returnPaths, H),
	loop_list_line(T).
	
loop_list_path([],_).
loop_list_path([H|T],Line):-
	loop_list_pathNode(H,Dur,Dist,LNo),
	DurF is Dur/60,
	DistF is Dist,
	%string_to_atom(H.key,K),
	assertz(linha(Line.name,H.key,LNo,DurF,DistF)),
	loop_list_path(T,Line).

loop_list_pathNode(LPathNodes,Dur,Dist,[A|LNo]):-
	loop_list_pathNode2(LPathNodes.pathNodes,0,Dur,0,Dist,LNo),
	LPathNodes.pathNodes = [H|_],
	atom_concat('https://api-mdr-lapr5.herokuapp.com/api/nodes/byid?id=', H.inicialNode, URL),
	http_get(URL, Reply, [json_object(dict)]),
	string_to_atom(Reply.shortName, A).

loop_list_pathNode2([],DurS,DurS,DistS,DistS,[]).	
loop_list_pathNode2([H|T],DurS,Dur,DistS,Dist,[A|LNo]):-
	Dur1 is DurS + H.duracao,
	Dist1 is DistS + H.distancia,
	atom_concat('https://api-mdr-lapr5.herokuapp.com/api/nodes/byid?id=', H.finalNode, URL),
	http_get(URL, Reply, [json_object(dict)]),
	string_to_atom(Reply.shortName, A),
	loop_list_pathNode2(T,Dur1,Dur,Dist1,Dist,LNo).

getHorarios():-
	http_get('https://api-mdr-lapr5.herokuapp.com/api/mock/all', Reply, [json_object(dict)]),
	retractall(horario(_,_)),
	loop_list_horario(Reply).
	
loop_list_horario([]).	
loop_list_horario([H|T]):-
	string_to_atom(H.path, P),
    assertz(horario(P, H.schedule)),
	loop_list_horario(T).

:-getNodes.
:-getLines.
:-getHorarios.


:- http_handler('/fastpath', send_fast_path, []).
server(Port) :-						
        http_server(http_dispatch, [port(Port)]).
			
:- json_object response(cam:string,custo:number).
send_fast_path(Request):-
	http_parameters(Request, 
						[orig(Orig, [atom]), 
						dest(Dest, [atom]), 
						hora(Hi, [number])
						]),
	aStar(Orig,Dest,Hi,Cam,Custo),
	term_string(Cam,A),
	R = response(A,Custo),
	prolog_to_json(R,RJSON),
	reply_json(RJSON, [json_object(dict)]).
	%format('Content-type: text/plain~n~n'),			
	%format('Cam: ~w~nCusto: ~w~n', [Cam,Custo]).

linha('Estação(Lordelo)_Lordelo',34,['ESTLO','LORDL'], 2,1500).
linha('Lordelo_Estação(Lordelo)',35,['LORDL','ESTLO'], 2,1500).
linha('Estação(Lordelo)_Sobrosa',36,['ESTLO','SOBRO'], 5,1500).
linha('Sobrosa_Estação(Lordelo)',37,['SOBRO','ESTLO'], 5,1800).
linha('Estação(Paredes)_Paredes',38,['ESTPA','PARED'], 2,1500).
linha('Paredes_Estação(Paredes)',39,['PARED','ESTPA'], 2,1500).

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
horario(35,[75840,75960]).
horario(35,[76320,76440]).
horario(24,[49140,49440,49680,49920,50160,50460]).
horario(24,[43860,44160,44400,44640,44880,45180]).
horario(20,[50760,51060,51300,51540,51780,52028]).
horario(20,[57360,57660,57900,58140,58380,58680]).
horario(37,[77160,77460]).

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
:-gera_ligacoes.

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


bestfs(Orig,Dest,Hi,Cam,Custo):-
	bestfs2(Orig,Dest,Hi,[],Cam),
	calcular_horario(Cam,Hi,0,Custo), !.

bestfs2(_,Dest,_,[(Act,Dest,Line)|T],Cam):- !,
	reverse([(Act,Dest,Line)|T],Cam).

bestfs2(Act,Dest,Hi,LA,Cam):-
	findall((EstX,Ha,[(Act,X,Line)|LA]),
		(liga(Act,X,Line),
		\+member((_,_,Line),LA),
		\+member((_,X,_),LA),
		\+member((X,_,_),LA),
		calcular_horario([(Act,X,Line)],Hi,0,T),
		Ha is Hi + T,
		estimativa_distancia(X,Dest,EstX)), 
		Novos),
	sort(Novos,NovosOrd),
	NovosOrd = [(_,Ha,Melhor)|_],
	Melhor = [(_,Orig2,_)|_],
	bestfs2(Orig2,Dest,Ha,Melhor,Cam).

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
	
printNode :- findall((T,D),(no(A,B,C,E,T,D),
			write('('), write(A), write(','), write(B), write(','), write(C), write(','), write(E), write(','), write(T), write(','), write(D), writeln(')')),
			LTempoDist).
printLine :- findall((T,D),(linha(B,C,E,T,D),
			write('('), write(B), write(','), write(C), write(','), write(E), write(','), write(T), write(','), write(D), writeln(')')),
			LTempoDist).
printHorario :- findall((T,D),(horario(T,D),
			write('('), write(T), write(','), write(D), writeln(')')),
			LTempoDist).
printLiga :- findall((T,D),(liga(E,T,D),
			write('('), write(E), write(','), write(T), write(','), write(D), writeln(')')),
			LTempoDist).