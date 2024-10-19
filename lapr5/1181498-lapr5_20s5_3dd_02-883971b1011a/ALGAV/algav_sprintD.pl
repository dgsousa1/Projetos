
:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic exec_time/1.
:-dynamic evaluation/1.
:-dynamic stabilization/1.
:-dynamic num_vehicle_duty/1.

% tarefa(Id,TempoProcessamento,TempConc,PesoPenalizacao).
tarefa(t1,2,5,1).
tarefa(t2,4,7,6).
tarefa(t3,1,11,2).
tarefa(t4,3,9,3).
tarefa(t5,3,8,2).

% tarefas(NTarefas).
tarefas(5).

% horario(Path,Trip,List_of_Time)
horario(38,459,[34080,34200]).
horario(3,31,[37800,38280,38580,38880,39420]).
horario(1,63,[39600,40140,40440,40740,41220]).
horario(3,33,[41400,41880,42180,42480,43020]).
horario(1,65,[43200,43740,44040,44340,44820]).
horario(3,35,[45000,45480,45780,46080,46620]).
horario(1,67,[46800,47340,47640,47940,48420]).
horario(3,37,[48600,49080,49380,49680,50220]).
horario(1,69,[50400,50940,51240,51540,52020]).
horario(3,39,[52200,52680,52980,53280,53820]).
horario(1,71,[54000,54540,54840,55140,55620]).
horario(3,41,[55800,56280,56580,56880,57420]).
horario(1,73,[57600,58140,58440,58740,59220]).
horario(3,43,[59400,59880,60180,60480,61020]).
horario(1,75,[61200,61740,62040,62340,62820]).
horario(3,45,[63000,63480,63780,64080,64620]).
horario(1,77,[64800,65340,65640,65940,66420]).
horario(3,48,[66600,67080,67380,67680,68220]).
horario(1,82,[68400,68940,69240,69540,70020]).
horario(3,52,[70200,70680,70980,71280,71820]).
horario(1,86,[72000,72540,72840,73140,73620]).
horario(3,56,[73800,74280,74580,74880,75420]).
horario(1,432,[75600,76140,76440,76740,77220]).
horario(39,460,[77220,77340]).

% workblock(WorkBlock, List_of_Trips, StartTime, EndTime)
workblock(12,[459],34080,37620).
workblock(211,[31,63],37620,41220).
workblock(212,[33,65],41220,44820).
workblock(213,[35,67],44820,48420).
workblock(214,[37,69],48420,52020).
workblock(215,[39,71],52020,55620).
workblock(216,[41,73],55620,59220).
workblock(217,[43,75],59220,62820).
workblock(218,[45,77],62820,66420).
workblock(219,[48,82],66420,70020).
workblock(220,[52,86],70020,73620).
workblock(221,[56,432],73620,77220).
workblock(222,[460],77220,77340).

% vehicleduty(VehicleDuty, List_of_WorkBlocks)
vehicleduty(12,[12,211,212,213,214,215,216,217,218,219,220,221,222]).
vehicleduty(13,[213,214,215,216,217,218,219,220]).
vehicleduty(14,[212,213,214,215,216,217,218,219,220,221]).

% lista_motoristas_nworkblocks(VehicleDuty, List_of_Drivers (Driver,N_of_WorkBlocks))
%lista_motoristas_nworkblocks(12,[(276,2),(5188,3),(16690,2),(18107,6)]).
:-dynamic lista_motoristas_nworkblocks/2.

% driver(Driver, StartWork, EndWork)
driver(276,28800,57600).
driver(5188,25200,54000).
driver(16690,39600,68400).
driver(18107,57600,86400).
driver(500,57600,86400).
driver(501,25200,54000).
driver(502,25200,54000).
driver(253,25200,54000).
driver(852,25200,54000).


% parameterização
inicializa:-
	write('Considerar o número de gerações condição de término? (1/0): '),read(CG),
	(CG == 1,
	write('Numero de novas Geracoes: '),read(NG), (retract(geracoes(_));true), asserta(geracoes(NG));
	(retract(geracoes(_));true), asserta(geracoes(10000000)), asserta(exec_time(300))),
	
	write('Dimensao da Populacao: '),read(DP),
	(retract(populacao(_));true), asserta(populacao(DP)),
	
	write('Vehicle Duty: '),read(VD),
	vehicleduty(VD,_),
	(retract(num_vehicle_duty(_));true), asserta(num_vehicle_duty(VD)),
	
	write('Probabilidade de Cruzamento (%):'), read(P1),
	PC is P1/100, 
	(retract(prob_cruzamento(_));true), asserta(prob_cruzamento(PC)),
	
	write('Probabilidade de Mutacao (%):'), read(P2),
	PM is P2/100, 
	(retract(prob_mutacao(_));true), asserta(prob_mutacao(PM)),
	
	write('Considerar o tempo de execução como condição de término? (1/0): '),read(CT),
	(CT == 1,
	write('Tempo de execução (s): '), read(T), (retract(exec_time(_));true), asserta(exec_time(T)); 
	(retract(exec_time(_));true), asserta(exec_time(10000000))),
	
	write('Considerar a avaliação da solução condição de término? (1/0): '),read(CE),
	(CE == 1,
	write('Avaliação da solução: '), read(E), (retract(evaluation(_));true), asserta(evaluation(E));
	(retract(evaluation(_));true), asserta(evaluation(-1))),
	
	write('Considerar a estabilização da população como condição de término? (1/0): '),read(CS),
	(CS == 1,
	write('Número de populações sem alteração: '), read(S), (retract(stabilization(_));true), asserta(stabilization(S));
	(retract(stabilization(_));true), asserta(stabilization(10000000))).


gera(PopF):-
	%inicializa,
	get_time(Ti),
	gera_populacao(Pop),
	%write('Pop='),write(Pop),nl,
	avalia_populacao(Pop,PopAv),
	%write('PopAv='),write(PopAv),nl,
	ordena_populacao(PopAv,PopOrd),
	geracoes(NG),
	populacao(TamPop),
	exec_time(T),
	evaluation(E),
	stabilization(S),
	gera_geracao(0,NG,TamPop,Ti,T,E,0,S,PopOrd),
	PopOrd = [PopF|_].



get_extended_list(List,FList):-
	get_extended_list(List,[],FListR),
	reverse(FListR,FList).
get_extended_list([],L,L).
get_extended_list([H|T],L,ExtendedList):-
	H = (Driver,Times),
	length(LApp,Times),
	maplist(=(Driver),LApp),
	append(LApp,L,L1),
	get_extended_list(T,L1,ExtendedList).


gera_populacao(Pop):-
	populacao(TamPop),
	num_vehicle_duty(VD),
	lista_motoristas_nworkblocks(VD,ListaDrivers),
	get_extended_list(ListaDrivers, ListaDriversE),
	length(ListaDriversE, NumT),
	gera_populacao(TamPop,ListaDriversE,NumT,Pop).

gera_populacao(0,_,_,[]):-!.

gera_populacao(TamPop,ListaTarefas,NumT,[Ind|Resto]):-
	TamPop1 is TamPop-1,
	gera_populacao(TamPop1,ListaTarefas,NumT,Resto),
	gera_individuo(ListaTarefas,NumT,Ind),
	not(member(Ind,Resto)).
gera_populacao(TamPop,ListaTarefas,NumT,L):-
	gera_populacao(TamPop,ListaTarefas,NumT,L).

gera_individuo([G],1,[G]):-!.

gera_individuo(ListaTarefas,NumT,[G|Resto]):-
	NumTemp is NumT + 1, % To use with random
	random(1,NumTemp,N),
	retira(N,ListaTarefas,G,NovaLista),
	NumT1 is NumT-1,
	gera_individuo(NovaLista,NumT1,Resto).

retira(1,[G|Resto],G,Resto).
retira(N,[G1|Resto],G,[G1|Resto1]):-
	N1 is N-1,
	retira(N1,Resto,G,Resto1).

avalia_populacao([],[]).
avalia_populacao([Ind|Resto],[Ind*V|Resto1]):-
	avalia(Ind,V),
	avalia_populacao(Resto,Resto1).

avalia(Seq,V):-
	num_vehicle_duty(VD),
	vehicleduty(VD, WBList),
	findall((A,B,C,D),(workblock(A,B,C,D), member(A,WBList)),WorkBlocks),
	get_tripletos(Seq,WorkBlocks,Tripletos), 
	lista_motoristas_nworkblocks(VD,ListDrivers),
	get_tripletos_per_driver(Tripletos,ListDrivers,TripletosPerDriver),
	avalia(TripletosPerDriver,0,V).

avalia([],VT,VT).
avalia([T|Resto],VT,V):-
	avaliaDriver(T,Av),
	V1 is VT + Av,
	avalia(Resto,V1,V).

avaliaDriver(List,Av):-
	avaliaDriver(List,0,Sum,0,Almoco,0,Jantar,0,0,VHorario,0,V), !,
	% > 8H NO TOTAL
	(Sum > 28800,
	VS is 10*Sum;
	VS is 0),
	Almoco1 is 14400-Almoco,
	(Almoco1 < 3600,
	VA is 8*Almoco1;
	VA is 0),
	Jantar1 is 14400-Jantar,
	(Jantar1 < 3600,
	VJ is 8*Jantar1;
	VJ is 0),
	Av is V+VS+VA+VJ+(8*VHorario).
avaliaDriver([],SumT,SumT,TAlmocoT,TAlmocoT,TJantarT,TJantarT,_,VHorarioT,VHorarioT,VT,VT).
avaliaDriver([TripletoDriver|Resto],SumT,Sum,TAlmocoT,TAlmoco,TJantarT,TJantar,LastStopAnterior,VHorarioT,VHorario,VT,V):-
	TripletoDriver = (StartTime,EndTime,Driver),
	ConsH is EndTime-StartTime,
	% > 4H CONSECUTIVO
	(ConsH > 14400,
	VCons is 10*(ConsH-14400);
	VCons is 0),
	% ALMOCO
	(
	(StartTime > 54000, TAlmoco1 is TAlmocoT);
	(EndTime < 39600, TAlmoco1 is TAlmocoT);
	(StartTime > 39600, EndTime < 54000, TAlmoco1 is TAlmocoT+(EndTime-StartTime));
	(StartTime > 39600, EndTime > 54000, TAlmoco1 is TAlmocoT+54000-StartTime);
	(StartTime < 39600, EndTime < 54000, TAlmoco1 is TAlmocoT+EndTime-39600);
	(StartTime < 39600, EndTime > 54000, TAlmoco1 is 0)
	),
	% JANTAR
	(
	(StartTime > 79200, TJantar1 is TJantarT);
	(EndTime < 64800, TJantar1 is TJantarT);
	(StartTime > 64800, EndTime < 79200, TJantar1 is TJantarT+(EndTime-StartTime));
	(StartTime > 64800, EndTime > 79200, TJantar1 is TJantarT+79200-StartTime);
	(StartTime < 64800, EndTime < 79200, TJantar1 is TJantarT+EndTime-64800);
	(StartTime < 64800, EndTime > 79200, TJantar1 is 0)
	),
	% DESCANSO
	TDescanso is StartTime-LastStopAnterior,
	(TDescanso < 3600,
	VDescanso is 10*(3600-TDescanso);
	VDescanso = 0),
	(ConsH >= 14400,
	LastStop is EndTime;
	LastStop is 0),
	% HORARIO
	driver(Driver,StartWork,EndWork),
	(
	(StartTime > StartWork, EndTime < EndWork, VHorario1 is VHorarioT);
	(EndTime < StartWork, VHorario1 is VHorarioT+ConsH);
	(StartTime > EndWork, VHorario1 is VHorarioT+ConsH);
	(StartTime < StartWork, EndTime < EndWork, VHorario1 is VHorarioT+StartWork-StartTime);
	(EndTime > EndWork, StartTime > StartWork, VHorario1 is VHorarioT+EndTime-EndWork);
	(VHorario1 is VHorarioT+StartWork-StartTime+EndTime-EndWork)
	),
	
	Sum1 is SumT+ConsH,
	V1 is VT+VCons+VDescanso,
	avaliaDriver(Resto,Sum1,Sum,TAlmoco1,TAlmoco,TJantar1,TJantar,LastStop,VHorario1,VHorario,V1,V).
	

% GET TRIPLETOS FOR EVALUATION
get_tripletos(Drivers,WorkBlocks,Tripletos):-
	get_tripletos(Drivers,WorkBlocks,[],TripletosR), !,
	reverse(TripletosR,Tripletos).
get_tripletos([],[],L,L).
get_tripletos([Driver|TD],[WorkBlock|TW],[],Tripletos):-
	WorkBlock = (_,_,StartTime,EndTime),
	get_tripletos(TD,TW,[(StartTime,EndTime,Driver)],Tripletos).
get_tripletos([Driver|TD],[WorkBlock|TW],[LH|LT],Tripletos):-
	WorkBlock = (_,_,StartTime,EndTime),
	LH = (StartTimeA,_,LastDriver),
	(LastDriver == Driver,
	get_tripletos(TD,TW,[(StartTimeA,EndTime,Driver)|LT],Tripletos);
	get_tripletos(TD,TW,[(StartTime,EndTime,Driver)|[LH|LT]],Tripletos)).
	
% GET LIST OF TRIPLETOS PER EACH DRIVER
get_tripletos_per_driver(Tripletos,ListDrivers,TripletosPerDriver):-
	get_tripletos_per_driver(Tripletos,ListDrivers,[],TripletosPerDriver),!.	
get_tripletos_per_driver(_,[],L,L).
get_tripletos_per_driver(Tripletos,[(Driver,_)|RestoDrivers],L,TripletosPerDriver):-
	get_list_of_tripletos(Tripletos,Driver,LT),
	reverse(LT,FinalLT),
	get_tripletos_per_driver(Tripletos,RestoDrivers,[FinalLT|L],TripletosPerDriver).
get_list_of_tripletos(Tripletos,Driver,LT):-
	get_list_of_tripletos(Tripletos,Driver,[],LT).
get_list_of_tripletos([],_,L,L).
get_list_of_tripletos([Tripleto|RestoTripletos],Driver,L,LT):-
	Tripleto = (_,_,D),
	(D == Driver, 
	get_list_of_tripletos(RestoTripletos,Driver,[Tripleto|L],LT);
	get_list_of_tripletos(RestoTripletos,Driver,L,LT)).

ordena_populacao(PopAv,PopAvOrd):-
	bsort(PopAv,PopAvOrd).

bsort([X],[X]):-!.
bsort([X|Xs],Ys):-
	bsort(Xs,Zs),
	btroca([X|Zs],Ys).


btroca([X],[X]):-!.

btroca([X*VX,Y*VY|L1],[Y*VY|L2]):-
	VX>VY,!,
	btroca([X*VX|L1],L2).

btroca([X|L1],[X|L2]):-btroca(L1,L2).


% REMOVE ULTIMO ELEMENTO DE UMA LISTA
list_butlast([X|Xs], Ys) :-                 
   list_butlast_prev(Xs, Ys, X).            

list_butlast_prev([], [], _).
list_butlast_prev([X1|Xs], [X0|Ys], X0) :-  
   list_butlast_prev(Xs, Ys, X1).

% TAKES THE FIRST N ELEMENTS FROM LIST
take(N, _, Xs) :- N =< 0, !, N =:= 0, Xs = [].
take(_, [], []).
take(N, [X|Xs], [X|Ys]) :- M is N-1, take(M, Xs, Ys).

% REMOVES ALL THE ELEMENTS OF ONE LIST
remove_list([], _, []).
remove_list([X|Tail], L2, Result):- member(X, L2), !, remove_list(Tail, L2, Result). 
remove_list([X|Tail], L2, [X|Result]):- remove_list(Tail, L2, Result).

% GENERATES RANDOM VALUES FOR ELEMENTS OF THE LIST
avalia_aleatoria([],[]).
avalia_aleatoria([List*Value|Xt],[List*RValue|Result]):-
	avalia_aleatoria(Xt,Result),
	random(R),
	RValue is Value*R.
	
get_real_values([],_,[]).
get_real_values([Sh*_|St],L,[H|Result]):-
	get_real_values(St,L,Result),
	get_real_values1(Sh,L,H).
	
get_real_values1(A,[A*Value|_],A*Value).
get_real_values1(A,[_|T],R):-
	get_real_values1(A,T,R).
	
	
% COMPARES LIST
compare_list([],[]).
compare_list([],_).
compare_list([L1Head|L1Tail], List2):-
    member(L1Head, List2), !,
    compare_list(L1Tail, List2).
	

gera_geracao(G,G,_,_,_,_,_,_,_):-!.
	%write('Geração '), write(G), write(':'), nl, write(Pop), nl,
	%write('Número total de gerações ('), write(G), write(') atingido').

% VERIFY IF EVALUATION IS MET
gera_geracao(_,_,_,_,_,E,_,_,Pop):-
	Pop = [_*Av|_],
	Av =< E.
	%write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	%write('Solução com avaliação ('), write(E), write(') ou menor atingido').
	
% VERIFY IF TIME IS UP
gera_geracao(_,_,_,Ti,T,_,_,_,_):-
	get_time(Ta),
	TSol is Ta-Ti,
	TSol >= T.
	%write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	%write('Tempo de execução definido ('), write(T), write('s) atingido').
	
% VERIFY IF POPULATION STABILIZED
gera_geracao(_,_,_,_,_,_,S,S,_).
	%write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	%write('População estabilizou durante ('), write(S), write(') gerações').

gera_geracao(N,G,TamPop,Ti,T,E,X,S,Pop):-
	%write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	
	Pop = [Melhor|_],
	random_permutation(Pop,PopR),
	cruzamento(PopR,NPop1),
	mutacao(NPop1,NPop),
	avalia_populacao(NPop,NPopAv),
	
	% NOT PURELY ELITIST
	append(Pop, NPopAv, PopJunta),
	sort(PopJunta, PopJuntaNDup),
	ordena_populacao(PopJuntaNDup, PopJuntaOrd),
	P is round(TamPop*0.3),
	take(P,PopJuntaOrd,PopMelhores),
	remove_list(PopJuntaOrd,PopMelhores,PopPiores),
	avalia_aleatoria(PopPiores, PopPioresR),
	ordena_populacao(PopPioresR, PopPioresROrd),
	M is TamPop-P,
	take(M,PopPioresROrd,PopPioresWithoutV),
	get_real_values(PopPioresWithoutV,PopPiores,PopPioresF),
	append(PopMelhores, PopPioresF, PopFinal),
	
	% ADDS THE BEST FROM LAST GENERATION AND REMOVES THE WORST
	(member(Melhor,PopFinal), ordena_populacao(PopFinal,NPopOrd2); 
	ordena_populacao([Melhor|PopFinal],NPopOrd), list_butlast(NPopOrd,NPopOrd2)),
	
	% VERIFIES IF THE OLD AND NEW GENERATIONS ARE EQUAL
	(compare_list(Pop,NPopOrd2), X1 is X+1; X1 is 0),
	
	N1 is N+1,
	gera_geracao(N1,G,TamPop,Ti,T,E,X1,S,NPopOrd2).

gerar_pontos_cruzamento(P1,P2):-
	gerar_pontos_cruzamento1(P1,P2).

gerar_pontos_cruzamento1(P1,P2):-
	num_vehicle_duty(VDNum), 
	vehicleduty(VDNum,List),
	length(List, NElem),
	Cortes is NElem/3,
	P1 is round(Cortes)+1,
	P2 is NElem-round(Cortes).
	
gerar_pontos_cruzamento1(P1,P2):-
	gerar_pontos_cruzamento1(P1,P2).


cruzamento([],[]).
cruzamento([Ind*_],[Ind]).
cruzamento([Ind1*_,Ind2*_|Resto],[NInd1,NInd2|Resto1]):-
	gerar_pontos_cruzamento(P1,P2),
	prob_cruzamento(Pcruz),random(0.0,1.0,Pc),
	((Pc =< Pcruz,!,
        cruzar(Ind1,Ind2,P1,P2,NInd1),
	  cruzar(Ind2,Ind1,P1,P2,NInd2))
	;
	(NInd1=Ind1,NInd2=Ind2)),
	cruzamento(Resto,Resto1).

preencheh([],[]).

preencheh([_|R1],[h|R2]):-
	preencheh(R1,R2).


sublista(L1,I1,I2,L):-
	I1 < I2,!,
	sublista1(L1,I1,I2,L).

sublista(L1,I1,I2,L):-
	sublista1(L1,I2,I1,L).

sublista1([X|R1],1,1,[X|H]):-!,
	preencheh(R1,H).

sublista1([X|R1],1,N2,[X|R2]):-!,
	N3 is N2 - 1,
	sublista1(R1,1,N3,R2).

sublista1([_|R1],N1,N2,[h|R2]):-
	N3 is N1 - 1,
	N4 is N2 - 1,
	sublista1(R1,N3,N4,R2).

rotate_right(L,K,L1):-
	num_vehicle_duty(VDNum), 
	vehicleduty(VDNum,List),
	length(List, N),
	T is N - K,
	rr(T,L,L1).

rr(0,L,L):-!.

rr(N,[X|R],R2):-
	N1 is N - 1,
	append(R,[X],R1),
	rr(N1,R1,R2).


elimina([],_,[]):-!.

elimina([X|R1],L,[X|R2]):-
	not(member(X,L)),!,
	elimina(R1,L,R2).

elimina([_|R1],L,R2):-
	elimina(R1,L,R2).

insere([],L,_,L):-!.
insere([X|R],L,N,L2):-
	num_vehicle_duty(VDNum), 
	vehicleduty(VDNum,List),
	length(List, T),
	((N>T,!,N1 is N mod T);N1 = N),
	insere1(X,N1,L,L1),
	N2 is N + 1,
	insere(R,L1,N2,L2).


insere1(X,1,L,[X|L]):-!.
insere1(X,N,[Y|L],[Y|L1]):-
	N1 is N-1,
	insere1(X,N1,L,L1).

remove_n(List,N,FList):-
	length(Prefix,N),
	append(Prefix,FList,List).

count([],_,0).
count([X|T],X,Y):- count(T,X,Z), Y is 1+Z.
count([X1|T],X,Z):- X1\=X,count(T,X,Z).


cruzar(Ind1,Ind2,P1,P2,NInd11):-
	sublista(Ind1,P1,P2,Sub1),
	num_vehicle_duty(VDNum), 
	vehicleduty(VDNum,List),
	length(List, NumT),
	R is NumT-P2,
	rotate_right(Ind2,R,Ind21),	
	eliminah(Sub1,Sub11),
	P4 is P1-1,
	take(P4,Ind21,Ind22),
	append(Sub11,Ind22,Sub12),
	remove_n(Ind21,P4,Ind23),
	take(R,Ind23,Ind24),
	remove_n(Ind23,R,Ind25),
	validate_cruzamento(Ind24,Ind25,Sub12,Ind26),
	append(Ind26,Sub12,NInd11).
	
validate_cruzamento([],_,[]).
validate_cruzamento(Ind24,Ind25,Sub12,Ind26):-
	num_vehicle_duty(VD),
	lista_motoristas_nworkblocks(VD,ListaDrivers),
	get_drivers_left(Sub12,ListaDrivers,DriversLeft),
	remove_list(Ind25,DriversLeft,Ind25W),
	remove_list(Ind25,Ind25W,Ind25T),
	get_list_to_append(Ind24,Ind25T,DriversLeft,[],ListToAppend), !,
	reverse(ListToAppend,Ind26).
	
get_list_to_append([],_,_,ListToAppendT,ListToAppendT).
get_list_to_append([H|T],Ind25,DriversLeft,ListToAppendT,ListToAppend):-
	(member(H,DriversLeft),
	delete_one(H,DriversLeft,DriversLeft1),
	delete_one(H,Ind25,Ind251),
	get_list_to_append(T,Ind251,DriversLeft1,[H|ListToAppendT],ListToAppend);
	Ind25 = [Header|Tail],
	(member(Header,DriversLeft), 
	delete_one(Header,DriversLeft,DriversLeft1),
	get_list_to_append(T,Tail,DriversLeft1,[Header|ListToAppendT],ListToAppend);
	DriversLeft = [DLH|DLT],
	get_list_to_append(T,Tail,DLT,[DLH|ListToAppendT],ListToAppend))).

delete_one(_, [], []).
delete_one(Term, [Term|Tail], Tail).
delete_one(Term, [Head|Tail], [Head|Result]) :-
  delete_one(Term, Tail, Result).
	
get_drivers_left(Sub12,ListaDrivers,DriversLeft):-
	get_drivers_left2(Sub12,ListaDrivers,[],DriversLeft).

get_drivers_left2(_,[],DriversLeftT,DriversLeftT).
get_drivers_left2(Sub12,[H|T],DriversLeftT,DriversLeft):-
	H = (Driver,Times),
	count(Sub12,Driver,Times2),
	(Times2 < Times,
	TimesT is Times - Times2,
	length(LApp, TimesT),
	maplist(=(Driver),LApp),
	append(LApp,DriversLeftT,DriversLeftT1),
	get_drivers_left2(Sub12,T,DriversLeftT1,DriversLeft);
	get_drivers_left2(Sub12,T,DriversLeftT,DriversLeft)).
	


eliminah([],[]).

eliminah([h|R1],R2):-!,
	eliminah(R1,R2).

eliminah([X|R1],[X|R2]):-
	eliminah(R1,R2).

mutacao([],[]).
mutacao([Ind|Rest],[NInd|Rest1]):-
	prob_mutacao(Pmut),
	random(0.0,1.0,Pm),
	((Pm < Pmut,!,mutacao1(Ind,NInd));NInd = Ind),
	mutacao(Rest,Rest1).

mutacao1(Ind,NInd):-
	gerar_pontos_cruzamento(P1,P2),
	mutacao22(Ind,P1,P2,NInd).

mutacao22([G1|Ind],1,P2,[G2|NInd]):-
	!, P21 is P2-1,
	mutacao23(G1,P21,Ind,G2,NInd).
mutacao22([G|Ind],P1,P2,[G|NInd]):-
	P11 is P1-1, P21 is P2-1,
	mutacao22(Ind,P11,P21,NInd).

mutacao23(G1,1,[G2|Ind],G2,[G1|Ind]):-!.
mutacao23(G1,P,[G|Ind],G2,[G|NInd]):-
	P1 is P-1,
	mutacao23(G1,P1,Ind,G2,NInd).
	
	
	
	

% SPRINT D
:-dynamic rangevd/3.
:-dynamic horariomotorista/5.

gerarDriverDuties:-
	tempo_trabalho_dos_vehicleduties(TVD),
	criar_driver_list(DriverList),
	tempo_trabalho_dos_drivers(DriverList,0,TD),
	TD >= 1.2 * TVD,
	distribuir_drivers(DriverList), !,
	criar_tuples(DriverTuples), !,
	get_vehicleduties(ListVehicleDuties),
	(retractall(lista_motoristas_nworkblocks(_,_));true), !,
	gerar_driver_workblock(DriverTuples,ListVehicleDuties),
	algoritmo_genetico(ListVehicleDuties,ListGenetico), !,
	write('ListGenetico: '), writeln(ListGenetico).

	
algoritmo_genetico(ListVehicleDuties,ListGenetico):-
	(retract(geracoes(_));true), asserta(geracoes(50)),
	(retract(populacao(_));true), asserta(populacao(10)),
	(retract(prob_cruzamento(_));true), asserta(prob_cruzamento(0.75)),
	(retract(prob_mutacao(_));true), asserta(prob_mutacao(0.05)),
	(retract(exec_time(_));true), asserta(exec_time(10000000)),
	(retract(evaluation(_));true), asserta(evaluation(-1)),
	(retract(stabilization(_));true), asserta(stabilization(10000000)), !,
	algoritmo_genetico1(ListVehicleDuties,[],ListGenetico).
	
algoritmo_genetico1([],ListGenetico1,ListGenetico1).
algoritmo_genetico1([VehicleDuty|T],ListGenetico1,ListGenetico):-
	VehicleDuty = (VD,_,_,_,_,_),
	(retract(num_vehicle_duty(_));true), asserta(num_vehicle_duty(VD)), !,
	gera(VehicleTypeDrivers),
	algoritmo_genetico1(T,[(VD,VehicleTypeDrivers)|ListGenetico1],ListGenetico).
	
tempo_trabalho_dos_drivers([],TD1,TD1).
tempo_trabalho_dos_drivers([Driver|T],TD1,TD):-
	Driver = (_,_,_,TTrabalho),
	TD2 is TD1 + TTrabalho,
	tempo_trabalho_dos_drivers(T,TD2,TD).


gerar_driver_workblock(DriverTuples,ListVehicleDuties):-
	ordena_vehicleduties_crescente(ListVehicleDuties, ListVehicleDutiesCresc),
	ordena_drivertuples_crescente(DriverTuples, DriverTuplesCresc),
	gerar_driver_workblock2(DriverTuplesCresc,ListVehicleDutiesCresc,[],ListVehicleDutiesAfter,DriverTuplesAfter), !,
	ordena_vehicleduties_decrescente(ListVehicleDutiesAfter, ListVehicleDutiesAfterDecresc),
	ordena_drivertuples_decrescente(DriverTuplesAfter, DriverTuplesDecresc),
	gerar_driver_workblock3(DriverTuplesDecresc,ListVehicleDutiesAfterDecresc,[],ListVehicleDutiesAfter1,DriverTuplesAfter1), !,
	(not(validate_vehicleduties_driver(ListVehicleDutiesAfter1)), !, 
	gerar_driver_workblock(DriverTuplesAfter1,ListVehicleDutiesAfter1);
	criar_lista_motorista_nworkblocks(ListVehicleDutiesAfter1)).


criar_lista_motorista_nworkblocks([]).
criar_lista_motorista_nworkblocks([VehicleDutyDriver|T]):-
	VehicleDutyDriver = (VD,_,_,_,_,List),
	asserta(lista_motoristas_nworkblocks(VD,List)),
	criar_lista_motorista_nworkblocks(T).
	


validate_vehicleduties_driver([]):-true. 
validate_vehicleduties_driver([VehicleDutyDriver|T]):-
	VehicleDutyDriver = (_,_,_,_,WBLeft,_),
	WBLeft == 0,  validate_vehicleduties_driver(T).
	
	

ordena_drivertuples_crescente(DriverTuples,DriverTuplesOrd):-
	esort(DriverTuples, 'C', DriverTuplesOrd), !.
	
ordena_drivertuples_decrescente(DriverTuples,DriverTuplesOrd):-
	esort(DriverTuples, 'D', DriverTuplesOrd), !.

esort([X],_,[X]):-!.
esort([X|Xs],Ord,Ys):-
	esort(Xs,Ord,Zs),
	(Ord == 'C',
	etroca([X|Zs],Ys) ;
	ftroca([X|Zs],Ys)).
	
etroca([X],[X]):-!.
etroca([(X,VX,M,N),(Y,VY,A,B)|L1],[(Y,VY,A,B)|L2]):-
	(X>Y;(X==Y,M<A)),!,
	etroca([(X,VX,M,N)|L1],L2).
etroca([X|L1],[X|L2]):-btroca(L1,L2).

ftroca([X],[X]):-!.
ftroca([(X,VX,M,N),(Y,VY,A,B)|L1],[(Y,VY,A,B)|L2]):-
	(VY>VX;(VX==VY,M<A)),!,
	ftroca([(X,VX,M,N)|L1],L2).
ftroca([X|L1],[X|L2]):-btroca(L1,L2).


gerar_driver_workblock3(DriverTuples,[],ListVehicleDutiesAfter1,ListVehicleDutiesAfter1,DriverTuples).
gerar_driver_workblock3(DriverTuples,[VehicleDuty|VDT],ListVehicleDutiesAfter1,ListVehicleDutiesAfter,DriverTuplesAfter):-
	VehicleDuty = (VD,FirstWB,LastWB,TWBMaior,NumWBs,List),
	(NumWBs > 0,
	get_frist_driver_last(VehicleDuty,DriverTuples,Driver),
	delete(DriverTuples,Driver,DriverTuples1),
	Driver = (_,_,TDriverVehicle,D),
	NumWBDriver is round(TDriverVehicle / TWBMaior),
	(NumWBs > NumWBDriver, List1 = [(D,NumWBDriver)|List], NumWBs1 is NumWBs - NumWBDriver ;
	List1 = [(D,NumWBs)|List], NumWBs1 is 0),
	gerar_driver_workblock2(DriverTuples1,VDT,[(VD,FirstWB,LastWB,TWBMaior,NumWBs1,List1)|ListVehicleDutiesAfter1],ListVehicleDutiesAfter,DriverTuplesAfter));
	gerar_driver_workblock2(DriverTuples,VDT,[VehicleDuty|ListVehicleDutiesAfter1],ListVehicleDutiesAfter,DriverTuplesAfter).
	
	
get_frist_driver_last(_,[],_).
get_frist_driver_last(VehicleDuty,[DriverTuple|T],Driver):-
	VehicleDuty = (_,_,LastWB,_,_,List),
	DriverTuple = (_,End,_,DT),
	(End >= LastWB, not(member(DT,List)), Driver = DriverTuple ;
	get_frist_driver_start(VehicleDuty,T,Driver)).
	

	
gerar_driver_workblock2(DriverTuples,[],ListVehicleDutiesAfter1,ListVehicleDutiesAfter1,DriverTuples).
gerar_driver_workblock2(DriverTuples,[VehicleDuty|VDT],ListVehicleDutiesAfter1,ListVehicleDutiesAfter,DriverTuplesAfter):-
	VehicleDuty = (VD,FirstWB,LastWB,TWBMaior,NumWBs,List),
	(NumWBs > 0,
	get_frist_driver_start(VehicleDuty,DriverTuples,Driver),
	delete(DriverTuples,Driver,DriverTuples1),
	Driver = (_,_,TDriverVehicle,D),
	NumWBDriver is round(TDriverVehicle / TWBMaior),
	(NumWBs > NumWBDriver, List1 = [(D,NumWBDriver)|List], NumWBs1 is NumWBs - NumWBDriver ;
	List1 = [(D,NumWBs)|List], NumWBs1 is 0),
	gerar_driver_workblock2(DriverTuples1,VDT,[(VD,FirstWB,LastWB,TWBMaior,NumWBs1,List1)|ListVehicleDutiesAfter1],ListVehicleDutiesAfter,DriverTuplesAfter));
	gerar_driver_workblock2(DriverTuples,VDT,[VehicleDuty|ListVehicleDutiesAfter1],ListVehicleDutiesAfter,DriverTuplesAfter).

	
	
	
get_frist_driver_start(_,[],_).
get_frist_driver_start(VehicleDuty,[DriverTuple|T],Driver):-
	VehicleDuty = (_,FirstWB,_,_,_,List),
	DriverTuple = (Start,_,_,DT),
	(FirstWB >= Start, not(member(DT,List)), Driver = DriverTuple ;
	get_frist_driver_start(VehicleDuty,T,Driver)).
	

	
get_vehicleduties(ListVehicleDuties):-
	findall((VD,FirstWB,LastWB,TWBMaior,NumWBs,[]),
			(rangevd(VD,FirstWB,LastWB),
			vehicleduty(VD,ListWB),
			length(ListWB, NumWBs),
			get_tempo_workblock_maior(ListWB, TWBMaior)),
			ListVehicleDuties).
			
get_tempo_workblock_maior(ListWB, TWBMaior):-
	get_tempo_workblock_maior2(ListWB, 0, TWBMaior), !.
	
get_tempo_workblock_maior2([], TWBMaior1, TWBMaior1).
get_tempo_workblock_maior2([WB|T], TWBMaior1, TWBMaior):-
	workblock(WB,_,StartWB,EndWB),
	TWB is EndWB - StartWB,
	(TWB > TWBMaior1, get_tempo_workblock_maior2(T,TWB,TWBMaior) ;
	get_tempo_workblock_maior2(T,TWBMaior1,TWBMaior)).
	
	
ordena_vehicleduties_crescente(ListVehicleDuties,ListVehicleDutiesOrd):-
	csort(ListVehicleDuties, 'C', ListVehicleDutiesOrd), !.
	
ordena_vehicleduties_decrescente(ListVehicleDuties,ListVehicleDutiesOrd):-
	csort(ListVehicleDuties, 'D', ListVehicleDutiesOrd), !.

csort([X],_,[X]):-!.
csort([X|Xs],Ord,Ys):-
	csort(Xs,Ord,Zs),
	(Ord == 'C',
	ctroca([X|Zs],Ys) ;
	dtroca([X|Zs],Ys)).

dtroca([X],[X]):-!.
dtroca([(X,VX,M,N,O),(Y,VY,A,B,C)|L1],[(Y,VY,A,B,C)|L2]):-
	M<A,!,
	dtroca([(X,VX,M,N,O)|L1],L2).
dtroca([X|L1],[X|L2]):-btroca(L1,L2).


ctroca([X],[X]):-!.
ctroca([(X,VX,M,N,O),(Y,VY,A,B,C)|L1],[(Y,VY,A,B,C)|L2]):-
	VX>VY,!,
	ctroca([(X,VX,M,N,O)|L1],L2).
ctroca([X|L1],[X|L2]):-btroca(L1,L2).

	
criar_tuples(DriverTuples):-
	findall((D,StartWork,EndWork,TTrabalho,Dist),
			horariomotorista(D,StartWork,EndWork,TTrabalho,Dist),
			DriverDist),
	criar_tuples1(DriverDist,[],DriverTuples).
	
criar_tuples1([],DriverTuples1,DriverTuples1).
criar_tuples1([Driver|T],DriverTuples1,DriverTuples):-
	Driver = (D,StartWork,EndWork,TTrabalho,Dist),
	criar_tuples2(D,StartWork,EndWork,TTrabalho,Dist,[],TuplesForDriver),
	append(TuplesForDriver,DriverTuples1,DriverTuples2),
	criar_tuples1(T,DriverTuples2,DriverTuples).
	
criar_tuples2(_,_,_,_,[],TuplesForDriver1,TuplesForDriver1).
criar_tuples2(D,StartWork,EndWork,TTrabalho,[Dist|T],TuplesForDriver1,TuplesForDriver):-
	EndWork1 is StartWork+Dist,
	criar_tuples2(D,EndWork1,EndWork,TTrabalho,T,[(StartWork,EndWork1,Dist,D)|TuplesForDriver1],TuplesForDriver).
	

tempo_trabalho_dos_vehicleduties(TVD):-
	(retractall(rangevd(_,_,_));true), !,
	findall(TimeVD,
			(vehicleduty(VD,ListWB),
			last(ListWB,LastWB),
			ListWB = [FirstWB|_],
			workblock(LastWB,_,_,LastWBFinal),
			workblock(FirstWB,_,FirstWBInitial,_),
			TimeVD is LastWBFinal - FirstWBInitial,
			asserta(rangevd(VD,FirstWBInitial,LastWBFinal))),
			VDTimeList),
	sum_list(VDTimeList, TVD).
	
	
criar_driver_list(DriverList):-
	findall((Driver,StartWork, EndWork, TTrabalho),
			(driver(Driver,StartWork,EndWork),
			TTrabalho is EndWork - StartWork),
			DriverList).
			
drivers_in_service(DriverList, TVD, DriversInService, SupportDrivers):-
	drivers_in_service2(DriverList, 0, TVD, [],DriversInService, SupportDrivers).
	
drivers_in_service2(DriverList, TVD1, TVD, DriversInService1, DriversInService1, DriverList):- TVD1 >= 2*TVD.
drivers_in_service2([Driver|T], TVD1, TVD, DriversInService1, DriversInService, DriverList):-
	Driver = (_, TTrabalho),
	TVD2 is TVD1 + TTrabalho,
	drivers_in_service2(T,TVD2,TVD, [Driver|DriversInService1], DriversInService, DriverList).
	
distribuir_drivers(DriverList):-
	(retractall(horariomotorista(_,_,_,_,_));true), !,
	distribuir_drivers1(DriverList).

distribuir_drivers1([]).
distribuir_drivers1([Driver|T]):-
	Driver = (DriverN, StartWork, EndWork, TTrabalho),
	TTrabalhoH is TTrabalho / 3600,
	random(0.5,0.75,R1), !,
	D1 is round(TTrabalhoH * R1),
	D2 is TTrabalhoH - D1,
	D1Sec is D1*3600,
	D2Sec is D2*3600,
	asserta(horariomotorista(DriverN,StartWork,EndWork,TTrabalho,[D1Sec,D2Sec])),
	distribuir_drivers1(T).

atribuir_drivers_vehicleduties(DriversDistribution):-
	findall((VehicleDuty,NumberWB),
			(vehicleduty(VehicleDuty,WorkBlocks),
			length(WorkBlocks, NumberWB)),
			VehicleDuties),
	colocar_maior_intervalo(DriversDistribution, VehicleDuties, [],VehicleDutiesProgress), !,
	writeln(VehicleDutiesProgress).
	

colocar_maior_intervalo(_, [], VehicleDutiesProgress1, VehicleDutiesProgress1).
colocar_maior_intervalo([D|DT], [VD|VDT], VehicleDutiesProgress1, VehicleDutiesProgress):-
	D = (DriverN,_,DriverDist),
	DriverDist = [BiggestInt|_],
	VD = (VehicleDutyN,_),
	colocar_maior_intervalo(DT, VDT, [(VehicleDutyN,[(DriverN, BiggestInt)])|VehicleDutiesProgress1], VehicleDutiesProgress).

	