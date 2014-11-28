<?php

/*
 * Script de génération d'un flux XML d'annonces ventes
 * Script dédié pour LEMONDE.FR
 * v1.0.0
 * OFOSTIER
 * 18/03/2013
 * 
 * v1.0.1
 * C.EGEA
 * 18/06/2013
 * Création du client qui permet au flux d'être dynamique
 * Le client de flux gère le requêtage et le document XML
 * Voir fichier de configuration ini pour saisie des paramètres partenaires
 * 
 * v1.10
 * 26/06/2013
 * Refacto du code 
 * 
 * 
 */

// error reporting
//ini_set('display_errors', 1);
//error_reporting(E_ALL | E_STRICT);

require_once('../modules/includes.php');
require_once (dirname(__FILE__) . '/Client.php');

$client = strtolower($_GET['client']);
$nomFlux = strtolower($_GET['flux_nom']);
$typeFlux = strtolower($_GET['flux_type']);
$typeDoc = strtolower($_GET['flux_doc']);
$debugMode = $_GET['modedebug'];


if (!empty($client) && !empty($typeFlux) && !empty($nomFlux)) {

    $paramClient = array(
        'client' => $client,
        'typeFlux' => $typeFlux,
        'nomFlux' => $nomFlux
    );

    try {
        $client = new Client($paramClient);
        
        if ($debugMode == 'pdosql') {
            $sSql = $client->SqlDebug();
        }
        
        $doc = $client->getDocument($typeDoc);
        echo $doc;
        
    } catch (Exception $e) {
        die($e->getMessage());
    }
} else {
    die('Error');
}
?>
