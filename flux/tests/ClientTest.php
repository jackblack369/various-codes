<?php

// error reporting
ini_set('display_errors', 1);
error_reporting(E_ALL | E_STRICT);

require_once '../Client.php';
//require_once('../../modules/includes.php');

class ClientTest extends PHPUnit_Framework_TestCase {

    /**
     * Teste le client de flux avec des paramètres erronés
     * @expectedExceptionCode 500
     * 
     */
    public function testConstruct() {

        try {
            $paramClient = array('client' => 'foo', 'typeFlux' => '35');
            $client = new Client($paramClient);
        } catch (Exception $expected) {
            return;
        }
        $this->fail('Une exception aurait du être levée');
    }
    /**
     * 
     * Un jeu de configuration valide
     */
    public static function provider() {
        return array(
            array('lexpress','ile_de_france', '1'),
            array('lexpress', 'reste_de_la_france','1'),
        );
    }

        /**
     * 
     * Un jeu de configuration non valide
     */
    public static function wrongProvider() {
        return array(
            array('Popi','borneo','5'),
            array('lexpress','toulon','8'),
            array('Geo','ankara','1'),
        );
    }

    /**
     * Teste le client de flux avec des paramètres corrects
     * @dataProvider provider
     */
    public function testSetSettings($nomClient, $nomFlux,$typeFlux) {

        try {
            $tabData = array('client' => $nomClient, 'nomFlux' => $nomFlux,'typeFlux' => $typeFlux);
            $client = new Client($tabData);
        } catch (Exception $e) {
            $this->fail($e->getLine());
            
        }
    }

    /**
     * Teste la génération de la requête avec des paramètres corrects
     * avec tous les champs dispo en base de données
     * @dataProvider provider
     */
    public function testGetSql($nomClient, $nomFlux,$typeFlux) {

        try {
            $tabData = array('client' => $nomClient, 'nomFlux' => $nomFlux,'typeFlux' => $typeFlux);
            $client = new Client($tabData);
            $this->assertEquals(!NULL, $client->getSql());
        } catch (Exception $e) {
            $this->fail('Problèmes de configuration ini');
        }
    }

    /**
     * Teste la génération de la requête avec des paramètres corrects
     * avec tous les champs dispo en base de données
     * @dataProvider provider
     */
    public function testGetSqlWithSelectedFields($nomClient, $nomFlux,$typeFlux) {

        try {
            $fields = array(
                'annonce',
                'default_town',
                'post_code',
                'photo',
                'property_type',
                'price',
                'nb_rooms',
                'area',
            );
            $tabData = array('client' => $nomClient, 'nomFlux' => $nomFlux,'typeFlux' => $typeFlux);
            $client = new Client($tabData);
            $this->assertEquals(!NULL, $client->getSql($fields));
        } catch (Exception $e) {
            $this->fail('Problèmes de configuration ini');
        }
    }

    /**
     * Teste la génération de la requête avec des paramètres corrects
     * avec certains champs dispo en base de données
     * @dataProvider wrongProvider
     */
    public function testGetSqlWrong($nomClient, $typeFlux) {

        try {
            $tabData = array('client' => $nomClient, 'typeFlux' => $typeFlux);
            $client = new Client($tabData);
        } catch (Exception $e) {
            return;
        }
        $this->fail('Une exception aurait du être levée');
    }
    
        /**
     * Teste la génération de la requête avec des paramètres corrects
     * avec tous les champs dispo en base de données
     * @dataProvider provider
     */
    public function testGetXml($nomClient, $nomFlux,$typeFlux) {

//        try {
//            $fields = array(
//                'annonce',
//                'default_town',
//                'post_code',
//                'photo',
//                'property_type',
//                'price',
//                'nb_rooms',
//                'area',
//            );
//            $tabData = array('client' => $nomClient, 'nomFlux' => $nomFlux,'typeFlux' => $typeFlux);
//            $client = new Client($tabData);
//            $sSql = $client->getSql($fields);
//            $this->assertEquals(!NULL,$client->getXml($sSql));
//        } catch (Exception $e) {
//            $this->fail('SQL');
//        }
    }

}
