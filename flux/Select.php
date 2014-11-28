<?php
/**
 * @author Christophe Egéa christophe.egea@hotmail.com
 * Classe utilitaire pour gérer le SELECT des requêtes
 */

class Select {
    
    /**
     * Retourne les champs nécessaires au flux
     * @param bool $showFields
     * @param string $table
     * @param string $tablePrefix
     * @return string
     */
    public function getFields($showFields = TRUE, $table = NULL, $tablePrefix) {

        if ($showFields) {
            $fields = '*';
            $select = "SELECT $fields";
        } else {
            $fields = array(
                'id_annonce',
                'default_town',
                'post_code',
                'photo',
                'property_type',
                'price',
                'nb_rooms',
                'area',
                'default_text',
            );

            foreach ($fields as &$v) {
                if (!($v == 'id_annonce')) {
                    $v = $tablePrefix . '_' . $v;
                }
            }
            $v = implode(',', $fields);
            $select = "SELECT $v";
        }

        if ($table) {
            $select .= " " . "FROM $table";
        }

        return $select;
    }
}