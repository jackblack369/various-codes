<?php
/**
 * @author Christophe Egéa christophe.egea@hotmail.com
 * Classe utilitaire pour gérer la table des requêtes
 */

class Table {

    protected $table;
    protected $tablePrefix;

    /**
     * Assigne la bonne table en fonction du type de flux
     * Renseigne le prefixe de table
     * @param integer $typeFlux
     */
    public function __construct($typeFlux) {

        if ($typeFlux > 1) {
            $this->table = 'T_RENTAD_RNT';
            $this->tablePrefix = 'rnt';
        } else {
            $this->tablePrefix = 'sal';
            $this->table = 'T_SALESAD_SAL';
        }
    }

    /**
     * Retourne le prefixe de table 
     * @return string
     */
    public function getTablePrefix() {

        return $this->tablePrefix;
    }

    /**
     * Retourne le nom de la table utilisé
     * @return string
     */
    public function getTable() {

        return $this->table;
    }

    /**
     * CLEAN UP ROUTINE...
     */
    public function __destruct() {

        $this->table = NULL;
        $this->tablePrefix = NULL;
    }
}