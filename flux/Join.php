<?php

/**
 * @author Christophe Egéa christophe.egea@hotmail.com
 * Classe utilitaire pour gérer les jointures des requêtes
 */
class Join {

    /**
     * Construit la jointure
     * @param string $table
     * @param string $tablePrefix
     * @param array $paramClient
     * @return string
     */
    public function constructJoins($selectObj = NULL, $table = '', $tablePrefix = '', array $paramClient) {

        if (!isset($paramClient['group_by_locality'])) {

            $join = 'WHERE';

            //LOCALITES
            if (isset($paramClient['in_localite_0'])) {
                $param[] = $tablePrefix . '_locality_0 IN (' . $paramClient['in_localite_0'] . ')';
            }
            if (isset($paramClient['in_localite_1'])) {
                $param[] = $tablePrefix . '_locality_1 IN (' . $paramClient['in_localite_1'] . ')';
            }
            if (isset($paramClient['in_localite_2'])) {
                $param[] = $tablePrefix . '_locality_2 IN (' . $paramClient['in_localite_2'] . ')';
            }
            if (isset($paramClient['in_lgn'])) {
                $param[] = 'lgn_id IN (' . $paramClient['in_lgn'] . ')';
            }

            if (isset($param) && (count($param) > 0)) {
                foreach ($param as $k => $v) {
                    if ($k == 0) {
                        $join .= ' ' . '(' . $v;
                    } else {
                        $join .= ' ' . 'OR' . ' ' . $v;
                    }
                }
                $join .= ')' . ' ' . 'AND';
            }

            // TYPE PROPRIETE
            $join .= $this->injectTypeJoin(NULL, $paramClient, $tablePrefix);
            //PRIX
            $join .= $this->injectPriceJoin('AND', $paramClient, $tablePrefix);
            // SURFACE
            $join .= $this->injectSurfaceJoin('AND', $paramClient, $tablePrefix);
            // EXCLUSIONS et INCLUSIONS
            $join .= $this->injectExclusionJoin('AND', $paramClient, $tablePrefix);
            // PIECES
            $join .= $this->injectRoomJoin('AND', $paramClient, $tablePrefix);
            // DATE CREATION
            $join .= $this->injectDateJoin('AND', TRUE, $tablePrefix);
            // NOMBRE D'ANNONCES A ENVOYER

            if (isset($paramClient['nb_ads'])) {
                $join .= $this->injectLimitJoin($paramClient['nb_ads']);
            }

            $select = $selectObj->getFields(FALSE, $table, $tablePrefix);
            $select .= ' ' . $join;

            return $select;
        } elseif (isset($paramClient['group_by_locality'])) {
            $select = $selectObj->getFields(TRUE, NULL, $tablePrefix);
            $select .= ' ' . 'FROM';
            $select .= "({$this->groupByLocalities($selectObj->getFields(TRUE, $table, $tablePrefix), $paramClient, $tablePrefix)})";
            $select .= "SUPER ORDER BY SUPER.{$tablePrefix}_update_date;";

            return $select;
        }
    }

    /**
     * 
     * Produit les requêtes à unire grâce au IN_LOCALITE_2
     * @return String
     */
    protected function groupByLocalities($select, array $paramClient, $tablePrefix) {

        if (isset($paramClient['in_localite_2'])) {

            $towns = explode(',', $paramClient['in_localite_2']);

            foreach ($towns as $k => $v) {
                $join = 'WHERE';

                //LOCALITES
                if (isset($paramClient['in_localite_0'])) {
                    $param[] = $tablePrefix . '_locality_0 IN (' . $paramClient['in_localite_0'] . ')';
                }
                if (isset($paramClient['in_localite_1'])) {
                    $param[] = $tablePrefix . '_locality_1 IN (' . $paramClient['in_localite_1'] . ')';
                }
                if (isset($paramClient['in_localite_2'])) {
                    $param[] = $tablePrefix . '_locality_2 IN (' . $v . ')';
                }

                if (isset($param) && (count($param) > 0)) {
                    foreach ($param as $k => $v) {
                        if ($k == 0) {
                            $join .= ' ' . '(' . $v;
                        } else {
                            $join .= ' ' . 'OR' . ' ' . $v;
                        }
                    }
                    $join .= ')' . ' ' . 'AND';
                }

                // TYPE PROPRIETE
                $join .= $this->injectTypeJoin(NULL, $paramClient, $tablePrefix);
                // PRIX
                $join .= $this->injectPriceJoin('AND', $paramClient, $tablePrefix);
                // SURFACE
                $join .= $this->injectSurfaceJoin('AND', $paramClient, $tablePrefix);
                // EXCLUSIONS et INCLUSIONS
                $join .= $this->injectExclusionJoin('AND', $paramClient, $tablePrefix);
                // PIECES
                $join .= $this->injectRoomJoin('AND', $paramClient, $tablePrefix);
                // DATE CREATION
                $join .= $this->injectDateJoin('AND', FALSE, $tablePrefix);
                // NOMBRE D'ANNONCES A ENVOYER
                if (isset($paramClient['group_by_locality'])) {
                    $join .= $this->injectLimitJoin($paramClient['group_by_locality']);
                }

                $queries[] = "({$select} {$join})";

                // On nettoie la jointure pour le prochain SELECT
                $join = NULL;
            }
        }

        // On rajoute les localités complexes
        return $this->makeUnion($select, $paramClient, $tablePrefix, $queries);
    }

    /**
     * Concatène des requêtes avec UNION 
     * @param string $select
     * @param array $paramClient
     * @param string $tablePrefix
     * @param array $queries
     * @return type
     */
    protected function makeUnion($select, array $paramClient, $tablePrefix = '', array $queries = NULL) {

        if ($union = $this->groupByLgn($select, $paramClient, $tablePrefix)) {
            foreach ($union as $v) {
                $queries[] = $v;
            }
        }

        if (count($queries) > 0) {
            $glue = ' ' . 'UNION' . ' ';
            return implode($glue, $queries);
        }
    }

    /**
     * Produit les requêtes à unire grâce au LGN_ID
     * @param type $select
     * @return array
     */
    protected function groupByLgn($select, array $paramClient, $tablePrefix = '') {

        if (isset($paramClient['in_lgn'])) {

            $towns = explode(',', $paramClient['in_lgn']);

            foreach ($towns as $k => $v) {
                $join = 'WHERE';

                //LOCALITES
                if (isset($paramClient['in_localite_0'])) {
                    $param[] = $tablePrefix . '_locality_0 IN (' . $paramClient['in_localite_0'] . ')';
                }
                if (isset($paramClient['in_localite_1'])) {
                    $param[] = $tablePrefix . '_locality_1 IN (' . $paramClient['in_localite_1'] . ')';
                }
                if (isset($paramClient['in_lgn'])) {
                    $param[] = 'lgn_id IN (' . $v . ')';
                }

                if (isset($param) && (count($param) > 0)) {
                    foreach ($param as $k => $v) {
                        if ($k == 0) {
                            $join .= ' ' . '(' . $v;
                        } else {
                            $join .= ' ' . 'OR' . ' ' . $v;
                        }
                    }
                    $join .= ')' . ' ' . 'AND';
                }

                // TYPE PROPRIETE
                $join .= $this->injectTypeJoin(NULL, $paramClient, $tablePrefix);
                //PRIX
                $join .= $this->injectPriceJoin('AND', $paramClient, $tablePrefix);
                // SURFACE
                $join .= $this->injectSurfaceJoin('AND', $paramClient, $tablePrefix);
                // EXCLUSIONS et INCLUSIONS
                $join .= $this->injectExclusionJoin('AND', $paramClient, $tablePrefix);
                // PIECES
                $join .= $this->injectRoomJoin('AND', $paramClient, $tablePrefix);
                // DATE CREATION
                $join .= $this->injectDateJoin('AND', FALSE, $tablePrefix);
                // NOMBRE D'ANNONCES A ENVOYER
                if (isset($paramClient['group_by_locality'])) {
                    $join .= $this->injectLimitJoin($paramClient['group_by_locality']);
                }

                //$select = $this->select;
                $queries[] = "({$select} {$join})";

                // On nettoie la jointure pour le prochain SELECT
                $join = NULL;
            }
        }

        return $queries;
    }

    /**
     * Prépare le critère de jointure sur les prix
     * @param string $glue
     * @param array $paramClient
     * @param string $tablePrefix
     * @return string
     */
    protected function injectPriceJoin($glue = '', array $paramClient, $tablePrefix = '') {
        // Prix max l'emporte sur 'nous consulter'
        if (isset($paramClient['prix_min']) && isset($paramClient['prix_max']) && isset($paramClient['nous_consulter'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=' . $paramClient['prix_min'] . ' ' . 'AND' . ' ' . $tablePrefix . '_price<=' . ' ' . $paramClient['prix_max'];
        } elseif (isset($paramClient['prix_min']) && (!isset($paramClient['prix_max'])) && isset($paramClient['nous_consulter'])) {
            if ($paramClient['nous_consulter'] == TRUE) {
                $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=' . $paramClient['prix_min'];
            } else {
                $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=' . $paramClient['prix_min'] . ' ' . 'AND' . ' ' . $tablePrefix . '_price<' . ' ' . client::NOUS_CONSULTER_PRIX;
            }
        } elseif (isset($paramClient['prix_min']) && isset($paramClient['prix_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=' . $paramClient['prix_min'] . ' ' . 'AND' . ' ' . $tablePrefix . '_price<=' . ' ' . $paramClient['prix_max'];
        } elseif (!isset($paramClient['prix_min']) && isset($paramClient['prix_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=0' . ' ' . 'AND' . ' ' . $tablePrefix . '_price<=' . ' ' . $paramClient['prix_max'];
        } elseif (isset($paramClient['prix_min'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_price >=' . $paramClient['prix_min'];
        } else {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_price >= 0';
        }

        return $join;
    }

    /**
     * Prépare le critère de jointure sur les surfaces
     * @param string $glue
     * @param array $paramClient
     * @param sring $tablePrefix
     * @return string
     */
    protected function injectSurfaceJoin($glue = '', array $paramClient, $tablePrefix = '') {
        // SURFACE
        if (isset($paramClient['surface_min']) && isset($paramClient['surface_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_area >' . $paramClient['surface_min'] . ' ' . 'AND sal_area <=' . $paramClient['surface_max'];
        } elseif (isset($paramClient['surface_min']) && (!isset($paramClient['surface_max']))) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_area >' . $paramClient['surface_min'];
        } elseif ((!isset($paramClient['surface_min'])) && isset($paramClient['surface_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_area > 0' . ' ' . 'AND' . ' ' . $tablePrefix . '_area <=' . $paramClient['surface_max'];
        } else {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_area > 0 AND' . ' ' . $tablePrefix . '_area IS NOT NULL';
        }
        return $join;
    }

    /**
     * Prépare le critère de jointure sur les pièces
     * @param string $glue
     * @param array $paramClient
     * @param string $tablePrefix
     * @return string
     */
    protected function injectRoomJoin($glue = '', array $paramClient, $tablePrefix = '') {

        if (isset($paramClient['nb_piece_min']) && isset($paramClient['nb_piece_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_nb_rooms >' . $paramClient['nb_piece_min'] . ' ' . 'AND' . ' ' . $tablePrefix . '_nb_rooms <=' . $paramClient['nb_piece_max'];
        } elseif (isset($paramClient['nb_piece_min']) && (!isset($paramClient['nb_piece_max']))) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_nb_rooms >' . $paramClient['nb_piece_min'];
        } elseif ((!isset($paramClient['nb_piece_min'])) && isset($paramClient['nb_piece_max'])) {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_nb_rooms > 0' . ' ' . 'AND' . ' ' . $tablePrefix . '_nb_rooms <=' . $paramClient['nb_piece_max'];
        } else {
            $join = ' ' . $glue . ' ' . $tablePrefix . '_nb_rooms > 0';
        }
        return $join;
    }

    /**
     * Prépare le critère de jointure sur les exclusion PRESTO et type de transactions
     * @param string $glue
     * @param array $paramClient
     * @param string $tablePrefix
     * @return string
     */
    protected function injectExclusionJoin($glue = '', array $paramClient, $tablePrefix = '') {

        $join = NULL;

        if (isset($paramClient['exclusion_presto'])) {
            $join .= ' ' . $glue . ' ' . 'id_presto NOT IN (' . $paramClient['exclusion_presto'] . ')';
        }

        if (isset($paramClient['type_transaction'])) {
            $join .= ' ' . $glue . ' ' . $tablePrefix . '_type_transaction IN (' . $paramClient['type_transaction'] . ')';
        } else {
            $join .= ' ' . $glue . ' ' . $tablePrefix . '_type_transaction NOT IN (4)';
        }
        return $join;
    }

    /**
     * Prépare le critère de jointure sur les types de propriétés
     * @param string $glue
     * @param array $paramClient
     * @param string $tablePrefix
     * @return string
     */
    protected function injectTypeJoin($glue = '', array $paramClient, $tablePrefix = '') {
        $join = ' ' . $glue . ' ' . $tablePrefix . '_property_type_id IN (' . $paramClient['type_de_bien'] . ')';
        $join .= ' ' . 'AND' . ' ' . $tablePrefix . '_nb_photo > 0';
        return $join;
    }

    /**
     * Prépare le critère de jointure sur les dates
     * @param string $glue
     * @param bool $orderBy
     * @param string $tablePrefix
     * @return string
     */
    protected function injectDateJoin($glue = '', $orderBy = TRUE, $tablePrefix = '') {
        $join = ' ' . $glue . ' ' . $tablePrefix . '_update_date < NOW() - INTERVAL 1 HOUR';
        if ($orderBy) {
            $join .= ' ' . 'ORDER BY' . ' ' . $tablePrefix . '_update_date DESC';
        }
        return $join;
    }

    /**
     * Prépare la limite de la requête
     * @param int $limit
     * @return string
     */
    protected function injectLimitJoin($limit = 0) {
        if (is_numeric($limit) && ($limit > 0)) {
            $join = ' ' . 'LIMIT' . ' ' . $limit;
            return $join;
        }
    }

}
