
:-dynamic geracoes/1.
:-dynamic populacao/1.
:-dynamic prob_cruzamento/1.
:-dynamic prob_mutacao/1.
:-dynamic exec_time/1.
:-dynamic evaluation/1.
:-dynamic stabilization/1.

% tarefa(Id,TempoProcessamento,TempConc,PesoPenalizacao).
tarefa(t1,2,5,1).
tarefa(t2,4,7,6).
tarefa(t3,1,11,2).
tarefa(t4,3,9,3).
tarefa(t5,3,8,2).

% tarefas(NTarefas).
tarefas(5).

% parameterização
inicializa:-
	write('Considerar o número de gerações condição de término? (1/0): '),read(CG),
	(CG == 1,
	write('Numero de novas Geracoes: '),read(NG), (retract(geracoes(_));true), asserta(geracoes(NG));
	(retract(geracoes(_));true), asserta(geracoes(10000000))),
	
	write('Dimensao da Populacao: '),read(DP),
	(retract(populacao(_));true), asserta(populacao(DP)),
	
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
	write('Avaliação da solução: '), read(E), (retract(evaluation(_));true), asserta(evaluation(E)),
	CG == 0, CT == 0, (retract(exec_time(_));true), asserta(exec_time(300));
	(retract(evaluation(_));true), asserta(evaluation(0))),
	
	write('Considerar a estabilização da população como condição de término? (1/0): '),read(CS),
	(CS == 1,
	write('Número de populações sem alteração: '), read(S), (retract(stabilization(_));true), asserta(stabilization(S)),
	CG == 0, CT == 0, (retract(exec_time(_));true), asserta(exec_time(300));
	(retract(stabilization(_));true), asserta(stabilization(10000000))).


gera:-
	inicializa,
	get_time(Ti),
	gera_populacao(Pop),
	write('Pop='),write(Pop),nl,
	avalia_populacao(Pop,PopAv),
	write('PopAv='),write(PopAv),nl,
	ordena_populacao(PopAv,PopOrd),
	geracoes(NG),
	populacao(TamPop),
	exec_time(T),
	evaluation(E),
	stabilization(S),
	gera_geracao(0,NG,TamPop,Ti,T,E,0,S,PopOrd).

gera_populacao(Pop):-
	populacao(TamPop),
	tarefas(NumT),
	findall(Tarefa,tarefa(Tarefa,_,_,_),ListaTarefas),
	gera_populacao(TamPop,ListaTarefas,NumT,Pop).

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
	avalia(Seq,0,V).

avalia([],_,0).
avalia([T|Resto],Inst,V):-
	tarefa(T,Dur,Prazo,Pen),
	InstFim is Inst+Dur,
	avalia(Resto,InstFim,VResto),
	(
		(InstFim =< Prazo,!, VT is 0)
  ;
		(VT is (InstFim-Prazo)*Pen)
	),
	V is VT+VResto.

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
	

gera_geracao(G,G,_,_,_,_,_,_,Pop):-!,
	write('Geração '), write(G), write(':'), nl, write(Pop), nl,
	write('Número total de gerações ('), write(G), write(') atingido').

% VERIFY IF EVALUATION IS MET
gera_geracao(N,_,_,_,_,E,_,_,Pop):-
	Pop = [_*Av|_],
	Av =< E,
	write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	write('Solução com avaliação ('), write(E), write(') ou menor atingido').
	
% VERIFY IF TIME IS UP
gera_geracao(N,_,_,Ti,T,_,_,_,Pop):-
	get_time(Ta),
	TSol is Ta-Ti,
	TSol >= T,
	write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	write('Tempo de execução definido ('), write(T), write('s) atingido').
	
% VERIFY IF POPULATION STABILIZED
gera_geracao(N,_,_,_,_,_,S,S,Pop):-
	write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	write('População estabilizou durante ('), write(S), write(') gerações').

gera_geracao(N,G,TamPop,Ti,T,E,X,S,Pop):-
	write('Geração '), write(N), write(':'), nl, write(Pop), nl,
	
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
	tarefas(N),
	NTemp is N+1,
	random(1,NTemp,P11),
	random(1,NTemp,P21),
	P11\==P21,!,
	((P11<P21,!,P1=P11,P2=P21);(P1=P21,P2=P11)).
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
	tarefas(N),
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
	tarefas(T),
	((N>T,!,N1 is N mod T);N1 = N),
	insere1(X,N1,L,L1),
	N2 is N + 1,
	insere(R,L1,N2,L2).


insere1(X,1,L,[X|L]):-!.
insere1(X,N,[Y|L],[Y|L1]):-
	N1 is N-1,
	insere1(X,N1,L,L1).

cruzar(Ind1,Ind2,P1,P2,NInd11):-
	sublista(Ind1,P1,P2,Sub1),
	tarefas(NumT),
	R is NumT-P2,
	rotate_right(Ind2,R,Ind21),
	elimina(Ind21,Sub1,Sub2),
	P3 is P2 + 1,
	insere(Sub2,Sub1,P3,NInd1),
	eliminah(NInd1,NInd11).


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














