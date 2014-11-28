<?php
/**
 * @author Christophe Egéa christophe.egea@hotmail.com
 * @description Classe utilitaire pour paramétrer les flux XML
 * FLUX TYPE : 1 = VENTE
 * FLUX TYPE : 2 = LOCATION
 *
 */
require_once(dirname(__FILE__) . '/Join.php');
require_once(dirname(__FILE__) . '/Select.php');
require_once(dirname(__FILE__) . '/Table.php');
require_once(dirname(__FILE__) . '/Document.php');

class Client {

    const NOUS_CONSULTER_PRIX = 2E9;
    const NB_ADS_DEFAULT = 500;

    protected $settings = NULL;
    protected $paramClient = NULL;
    protected $rechParamsSection = NULL;
    public $xmlDoc = NULL;

    /**
     * Recherche la section approriée dans le fichier de conf ini
     * @param array $paramClient
     */
    public function __construct(array $paramClient = NULL) {

        if ($this->getParamIniSection($paramClient)) {
            $this->setSettings();
        }
    }

    /**
     * Récupère les paramètres de configuration du fichier INI
     * @param array $paramClient
     * @return array
     * @throws Exception
     */
    protected function getParamIniSection(array $paramClient = NULL) {

        $file = dirname(__FILE__) . "/conf/{$paramClient['client']}.ini";

        if (file_exists($file) && is_readable($file)) {
            $this->settings = parse_ini_file($file, TRUE);
        } else {
            throw new Exception('Erreur 3000 - Configuration inexistante ou erreur paramétrage', 3000);
        }

        // On recherche la bonne section de paramètres 
        $this->rechParamsSection = $paramClient['client'] . '_' . $paramClient['nomFlux'];

        // On vérifie le type de flux et son nom
        if (($this->settings["$this->rechParamsSection"]['type_flux'] == $paramClient['typeFlux']) && ($this->settings["$this->rechParamsSection"]['nom_flux'] == $paramClient['nomFlux'])) {
            return $this->rechParamsSection;
        } else {
            throw new Exception('Erreur 3001 - Flux inexistant', 3001);
        }
    }

    /**
     * Enregistre les réglages lus dans le fichier de configuration ini
     * @return void
     */
    protected function setSettings() {

        if (array_key_exists($this->rechParamsSection, $this->settings)) {
            $this->paramClient = array();

            if ((isset($this->settings["$this->rechParamsSection"]['type_flux'])) && ($this->settings["$this->rechParamsSection"]['type_flux'] >= 0)) {
                $this->paramClient['type_flux'] = $this->settings["$this->rechParamsSection"]['type_flux'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['type_de_bien'])) && ($this->settings["$this->rechParamsSection"]['type_de_bien'] >= 0)) {
                $this->paramClient['type_de_bien'] = $this->settings["$this->rechParamsSection"]['type_de_bien'];
            } else {
                throw new Exception('Erreur configuration - \'type bien obligatoire\'', 3002);
            }

            if ((isset($this->settings["$this->rechParamsSection"]['prix_min'])) && ($this->settings["$this->rechParamsSection"]['prix_min'] >= 0)) {
                $this->paramClient['prix_min'] = $this->settings["$this->rechParamsSection"]['prix_min'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['prix_max'])) && ($this->settings["$this->rechParamsSection"]['prix_max'] >= 0)) {
                $this->paramClient['prix_max'] = $this->settings["$this->rechParamsSection"]['prix_max'];
            }

            if (isset($this->settings["$this->rechParamsSection"]['nous_consulter'])) {
                if ((strtoupper($this->settings["$this->rechParamsSection"]['nous_consulter']) == TRUE) || (strtoupper($this->settings["$this->rechParamsSection"]['nous_consulter']) == 1)) {
                    $this->paramClient['nous_consulter'] = TRUE;
                } else {
                    $this->paramClient['nous_consulter'] = FALSE;
                }
            }

            if (isset($this->settings["$this->rechParamsSection"]['description'])) {
                $this->paramClient['description'] = $this->settings["$this->rechParamsSection"]['description'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['surface_min'])) && ($this->settings["$this->rechParamsSection"]['surface_min'] >= 0)) {
                $this->paramClient['surface_min'] = $this->settings["$this->rechParamsSection"]['surface_min'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['surface_max'])) && ($this->settings["$this->rechParamsSection"]['surface_max'] >= 0)) {
                $this->paramClient['surface_max'] = $this->settings["$this->rechParamsSection"]['surface_max'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['nb_piece_min'])) && ($this->settings["$this->rechParamsSection"]['nb_piece_min'] >= 0)) {
                $this->paramClient['nb_piece_min'] = $this->settings["$this->rechParamsSection"]['nb_piece_min'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['nb_piece_max'])) && ($this->settings["$this->rechParamsSection"]['nb_piece_max'] >= 0)) {
                $this->paramClient['nb_piece_max'] = $this->settings["$this->rechParamsSection"]['nb_piece_max'];
            }

            if ((isset($this->settings["$this->rechParamsSection"]['nb_ads'])) && ($this->settings["$this->rechParamsSection"]['nb_ads'] >= 0)) {
                $this->paramClient['nb_ads'] = $this->settings["$this->rechParamsSection"]['nb_ads'];
            } else {
                $this->paramClient['nb_ads'] = Client::NB_ADS_DEFAULT;
            }

            if ((isset($this->settings["$this->rechParamsSection"]['exclusion_presto'])) && ($this->settings["$this->rechParamsSection"]['exclusion_presto'] >= 0)) {
                $this->paramClient['exclusion_presto'] = $this->settings["$this->rechParamsSection"]['exclusion_presto'];
            }

            if (isset($this->settings["$this->rechParamsSection"]['format_photo'])) {
                $this->paramClient['format_photo'] = $this->settings["$this->rechParamsSection"]['format_photo'];
            } else {
                throw new Exception('Erreur configuration - \'format photo obligatoire\'', 3002);
            }

            if (isset($this->settings["$this->rechParamsSection"]['url_root_img'])) {
                $this->paramClient['url_root_img'] = $this->settings["$this->rechParamsSection"]['url_root_img'];
            } else {
                throw new Exception('Erreur configuration - \'url de photo obligatoire\'', 3002);
            }

            if (isset($this->settings["$this->rechParamsSection"]['group_by_locality'])) {
                $this->paramClient['group_by_locality'] = $this->settings["$this->rechParamsSection"]['group_by_locality'];
            }

            // LOCALITES
            // Niveau région 
            if (isset($this->settings["$this->rechParamsSection"]['in_localite_0'])) {
                $this->paramClient['in_localite_0'] = $this->settings["$this->rechParamsSection"]['in_localite_0'];
            }
            // Niveau département
            if (isset($this->settings["$this->rechParamsSection"]['in_localite_1'])) {
                $this->paramClient['in_localite_1'] = $this->settings["$this->rechParamsSection"]['in_localite_1'];
            }
            // Niveau ville
            if (isset($this->settings["$this->rechParamsSection"]['in_localite_2'])) {
                $this->paramClient['in_localite_2'] = $this->settings["$this->rechParamsSection"]['in_localite_2'];
            }

            // LOCALITES COMPLEXES
            if (isset($this->settings["$this->rechParamsSection"]['in_lgn'])) {
                $this->paramClient['in_lgn'] = $this->settings["$this->rechParamsSection"]['in_lgn'];
            }

            // URL des ventes et vérification type flux
            if ((isset($this->settings["$this->rechParamsSection"]['url_sal_ads'])) && ($this->paramClient['type_flux']) <= 1) {
                $this->paramClient['url_sal_ads'] = $this->settings["$this->rechParamsSection"]['url_sal_ads'];
            }
            if ((!isset($this->settings["$this->rechParamsSection"]['url_sal_ads'])) && ($this->paramClient['type_flux']) <= 1) {
                throw new Exception('Erreur configuration - url annonce "sal" obligatoire', 3002);
            }

            // URL des locations et vérification type flux
            if ((isset($this->settings["$this->rechParamsSection"]['url_rnt_ads'])) && ($this->paramClient['type_flux']) > 1) {
                $this->paramClient['url_rnt_ads'] = $this->settings["$this->rechParamsSection"]['url_rnt_ads'];
            }
            // Vérification de la présence du paramètre 'url_sal_ads'
            if ((!isset($this->settings["$this->rechParamsSection"]['url_rnt_ads'])) && ($this->paramClient['type_flux']) > 1) {
                throw new Exception('Erreur configuration - url annonce "rnt" obligatoire', 3002);
            }

            // LOCALITES COMPLEXES
            if (isset($this->settings["$this->rechParamsSection"]['type_transaction'])) {
                $this->paramClient['type_transaction'] = $this->settings["$this->rechParamsSection"]['type_transaction'];
            }
        } else {
            throw new Exception('Erreur configuration - Paramétrage défectueux', 3002);
        }
    }
    
    /**
     * Vérifie si les paramètres dans le fichier ini sont bien présents
     * @return boolean
     */
    protected function isSetByIni() {

        $isSetOk = TRUE;

        if (count($this->paramClient) > 0) {
            foreach ($this->paramClient as $k => $v) {
                if ((!isset($v)) || (!$this->isCorrectType($k, $v))) {
                    $isSetOk = FALSE;
                }
            }
            return $isSetOk;
        }
    }

    /**
     * Vérifie les types des paramètres renseignés
     * @param string $key
     * @param string $value
     * @return boolean
     */
    protected function isCorrectType($key, $value) {

        $tab = array();

        switch ($key) {
            case 'in_localite_0':
                return $this->is_numeric_2($value);
                break;
            case 'in_localite_1':
                return $this->is_numeric_2($value);
                break;
            case 'in_localite_2':
                return $this->is_numeric_2($value);
                break;
            case 'in_lgn':
                return $this->is_numeric_2($value);
                break;
            case 'type_de_bien':
                return $this->is_numeric_2($value);
                break;
            case 'exclusion_presto':
                return $this->is_numeric_2($value);
                break;
            case 'type_transaction':
                return $this->is_numeric_2($value);
                break;
            case 'group_by_locality':
                return is_numeric($value);
                break;
            case 'nous_consulter':
                return is_bool($value);
                break;
            case 'description':
                return is_numeric($value);
                break;
            case 'format_photo':
                return is_string($value);
                break;
            case 'url_root_img':
                return is_string($value);
                break;
            case 'url_sal_ads':
                return is_string($value);
                break;
            case 'url_rnt_ads':
                return is_string($value);
                break;
            default :
                return is_numeric($value);
        }
    }

    /**
     * Alias de is_numeric pour traiter une liste d'entiers
     * @param string $value
     * @return boolean
     */
    protected function is_numeric_2($value) {

        $tab = explode(',', $value);

        foreach ($tab as $va) {
            if (is_numeric($va)) {
                continue;
            } else {
                return FALSE;
            }
        }
        return TRUE;
    }

    /**
     * Permet de visualiser la requête SQL
     * @return String
     */
    public function SqlDebug() {
        
        if($this->isSetByIni()){
            $tableObj = new Table($this->paramClient['type_flux']);
            $selectObj = new Select();
            $joinObj = new Join();
            die($joinObj->constructJoins($selectObj,$tableObj->getTable(), $tableObj->getTablePrefix(), $this->paramClient));
        }
    }
    
    /**
     * Génrère le document pour flux partenaire
     * @param string $sSql
     * @return xmlDoc
     */
    public function getDocument($format = '') {

        if($this->isSetByIni()){
            $tableObj = new Table($this->paramClient['type_flux']);
            $selectObj = new Select();
            $joinObj = new Join();
            $sSql = $joinObj->constructJoins($selectObj,$tableObj->getTable(), $tableObj->getTablePrefix(), $this->paramClient);
            $documentObj = new Document($sSql, $tableObj->getTablePrefix() ,$this->paramClient);
            
            return $documentObj->getDocument($format);
        }
    }
    
    /**
     * CLEAN UP ROUTINE...
     */
    public function __destruct() {

        $this->paramClient = NULL;
        $this->settings = NULL;
        $this->rechParamsSection = NULL;
    }
}