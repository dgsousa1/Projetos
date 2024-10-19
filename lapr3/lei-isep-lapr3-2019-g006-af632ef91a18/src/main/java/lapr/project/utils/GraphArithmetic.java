/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lapr.project.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import lapr.project.controller.ParkController;
import lapr.project.model.Client;
import lapr.project.model.Location;
import lapr.project.model.POI;
import lapr.project.model.Park;
import lapr.project.model.Path;
import lapr.project.model.Vehicle;
import static lapr.project.utils.GraphAlgorithms.shortestPath;

/**
 *
 * @author Nuno
 */
public class GraphArithmetic {

//    public static double getShortestPathPassingInPOIs(List<POI> pois, Graph<Location, Path> graph, Park parkOrg,
//            Park parkDest, Map<List<Location>, Double> map) {
//        LinkedList<LinkedList<Location>> arrayCombinacoes = new LinkedList<>();
//        LinkedList<Location> passage = new LinkedList<>();
//        Location verts[] = graph.allkeyVerts();
//        for (POI p : pois) {
//            for (int i = 0; i < verts.length; i++) {
//                if(p.getLocation().equals(verts[i])){
//                    passage.add(verts[i]);
//                }
//            }
//        }
//        possibleCombinations(passage, arrayCombinacoes, 0);
//
//        //Listas com todos os percursos possíveis e distancias de cada percurso
//        LinkedList<LinkedList<Location>> arrayPercursos = new LinkedList<>();
//        LinkedList<Double> arrayDistancias = new LinkedList<>();
//
//        //Calcula todos os percursos para as combinações possíveis
//        for (int i = 0; i < arrayCombinacoes.size(); i++) {
//            double distancia = 0;
//            LinkedList<Location> caminhoIntermedio = new LinkedList<>();
//            LinkedList<Location> caminho = new LinkedList<>();
//            distancia += shortestPath(graph, parkOrg.getLocation(), arrayCombinacoes.get(i).get(0), caminhoIntermedio);
//            //Condição valida se algum dos paises intermédios é inválido
//            if (caminhoIntermedio.isEmpty()) {
//                break;
//            }
//            caminho.addAll(caminhoIntermedio);
//            for (int j = 0; j < arrayCombinacoes.get(i).size() - 1; j++) {
//                caminhoIntermedio.clear();
//                distancia += shortestPath(graph, arrayCombinacoes.get(i).get(j), arrayCombinacoes.get(i).get(j + 1), caminhoIntermedio);
//                if (caminhoIntermedio.isEmpty()) {
//                    break;
//                }
//                if (caminho.getLast().equals(caminhoIntermedio.getFirst())) {
//                    caminhoIntermedio.removeFirst();
//                }
//                caminho.addAll(caminhoIntermedio);
//                caminhoIntermedio.clear();
//            }
//            caminhoIntermedio.clear();
//            distancia += shortestPath(graph, arrayCombinacoes.get(i).getLast(), parkDest.getLocation(), caminhoIntermedio);
//            if (caminhoIntermedio.isEmpty()) {
//                break;
//            }
//            if (caminho.getLast().equals(caminhoIntermedio.getFirst())) {
//                caminhoIntermedio.removeFirst();
//            }
//            caminho.addAll(caminhoIntermedio);
//            arrayPercursos.add(caminho);
//            arrayDistancias.add(distancia);
//        }
//
//        for (int i = 0; i < arrayPercursos.size(); i++) {
//            if (!validatePath(arrayPercursos.get(i), graph)) {
//                arrayPercursos.remove(i);
//                arrayDistancias.remove(i);
//            }
//        }
//        //Descobrir qual é o melhor caminho
//        double distanciaFinal = 0;
//        if (!arrayPercursos.isEmpty()) {
//            distanciaFinal = Double.MAX_VALUE;
//            for (int i = 0; i < arrayDistancias.size(); i++) {
//                map.put(arrayPercursos.get(i), arrayDistancias.get(i));
//                if (arrayDistancias.get(i) < distanciaFinal) {
//                    distanciaFinal = arrayDistancias.get(i);
//                }
//            }
//        }
//        return distanciaFinal;
//    }
    public static double getShortestPathPassingInPOIs(List<POI> pois, Graph<Location, Path> graph, Park parkOrg,
            Park parkDest, List<Location> shortPath) {
        LinkedList<LinkedList<Location>> arrayCombinacoes = new LinkedList<>();
        LinkedList<Location> passage = new LinkedList<>();
        Location verts[] = graph.allkeyVerts();
        for (POI p : pois) {
            for (int i = 0; i < verts.length; i++) {
                if (p.getLocation().equals(verts[i])) {
                    passage.add(verts[i]);
                }
            }
        }
        possibleCombinations(passage, arrayCombinacoes, 0);

        //Listas com todos os percursos possíveis e distancias de cada percurso
        LinkedList<LinkedList<Location>> arrayPercursos = new LinkedList<>();
        LinkedList<Double> arrayDistancias = new LinkedList<>();

        //Calcula todos os percursos para as combinações possíveis
        for (int i = 0; i < arrayCombinacoes.size(); i++) {
            double distancia = 0;
            LinkedList<Location> caminhoIntermedio = new LinkedList<>();
            LinkedList<Location> caminho = new LinkedList<>();
            distancia += shortestPath(graph, parkOrg.getLocation(), arrayCombinacoes.get(i).get(0), caminhoIntermedio);
            //Condição valida se algum dos paises intermédios é inválido
            if (caminhoIntermedio.isEmpty()) {
                break;
            }
            caminho.addAll(caminhoIntermedio);
            for (int j = 0; j < arrayCombinacoes.get(i).size() - 1; j++) {
                caminhoIntermedio.clear();
                distancia += shortestPath(graph, arrayCombinacoes.get(i).get(j), arrayCombinacoes.get(i).get(j + 1), caminhoIntermedio);
                if (caminhoIntermedio.isEmpty()) {
                    break;
                }
                if (caminho.getLast().equals(caminhoIntermedio.getFirst())) {
                    caminhoIntermedio.removeFirst();
                }
                caminho.addAll(caminhoIntermedio);
                caminhoIntermedio.clear();
            }
            caminhoIntermedio.clear();
            distancia += shortestPath(graph, arrayCombinacoes.get(i).getLast(), parkDest.getLocation(), caminhoIntermedio);
            if (caminhoIntermedio.isEmpty()) {
                break;
            }
            if (caminho.getLast().equals(caminhoIntermedio.getFirst())) {
                caminhoIntermedio.removeFirst();
            }
            caminho.addAll(caminhoIntermedio);
            arrayPercursos.add(caminho);
            arrayDistancias.add(distancia);
        }

        for (int i = 0; i < arrayPercursos.size(); i++) {
            if (!validatePath(arrayPercursos.get(i), graph)) {
                arrayPercursos.remove(i);
                arrayDistancias.remove(i);
            }
        }
        //Descobrir qual é o melhor caminho
        double distanciaFinal = 0;
        if (!arrayPercursos.isEmpty()) {
            distanciaFinal = Double.MAX_VALUE;
            for (int i = 0; i < arrayDistancias.size(); i++) {
                if (arrayDistancias.get(i) < distanciaFinal) {
                    distanciaFinal = arrayDistancias.get(i);
                    shortPath.clear();
                    shortPath.addAll(arrayPercursos.get(i));
                }
            }
        }
        return distanciaFinal;
    }

    private static void possibleCombinations(LinkedList<Location> passage, LinkedList<LinkedList<Location>> arrayCombinations, int index) {
        //Verifica se já está no último elemento, com mais nada para permutar
        if (index >= passage.size() - 1) {
            if (passage.size() > 0) {
                //Adiciona uma cópia da lista de Estações intermédias à lista que guarda todas as combinações
                arrayCombinations.add((LinkedList<Location>) passage.clone());
            }
            return;
        }
        //Para cada índice no sub array restricoes[index...final]
        for (int i = index; i < passage.size(); i++) {
            //Troca dos elementos nos índices index e i
            Location est = passage.get(index);
            passage.set(index, passage.get(i));
            passage.set(i, est);
            //Método recursivo que recebe como um dos parametros o subarray restricoes[index+1...final]
            possibleCombinations(passage, arrayCombinations, index + 1);
            //Troca os elementos outra vez
            est = passage.get(index);
            passage.set(index, passage.get(i));
            passage.set(i, est);
        }
    }

    private static boolean validatePath(List<Location> list, Graph<Location, Path> graph) {
        try {
            getPaths(list, graph);
        } catch (NullPointerException e) {
            Logger.getLogger(ParkController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }

    public static List<Path> getPaths(List<Location> list, Graph<Location, Path> graph) {
        List<Path> paths = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            paths.add(graph.getEdge(list.get(i), list.get(i + 1)).getElement());
        }
        return paths;
    }

    public static List<List<Location>> getPathWithMinDistance(double distance, Graph<Location, Path> graph, Park parkOrg, Park parkDest, Client client, Vehicle vehicle) {
        List<List<Location>> shortPaths = new ArrayList<>();
        ArrayList<LinkedList<Location>> allPaths = GraphAlgorithms.allPaths(graph, parkOrg.getLocation(), parkDest.getLocation());
        double routeDistance;
        for (List<Location> path : allPaths) {
            Collections.reverse(path);
            routeDistance = Calculator.calculatePathEnergy(path, graph, client, vehicle);
            if (routeDistance == distance) {
                shortPaths.add(path);
            }
        }
        return shortPaths;
    }
}
