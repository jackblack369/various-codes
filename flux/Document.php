<?php

/**
 * @author Christophe Egéa christophe.egea@hotmail.com
 * 
 * Classe utilitaire pour gérer les différents documents pour flux
 * partenaires, XML, JSON, etc ...
 */
class Document {

    protected $paramClient;
    protected $tablePrefix;
    protected $xmlDoc;
    protected $sSql;

    public function __construct($sSql, $tablePrefix, $paramClient) {

        $this->paramClient = $paramClient;
        $this->tablePrefix = $tablePrefix;
        $this->sSql = $sSql;
    }

    /**
     * Permet d'obtenir le document au format souhaité
     * @param string $format
     * @return string
     */
    public function getDocument($format = '') {
        switch (strtoupper($format)) {
            case 'XML':
                return $this->getXml();
                break;
            case 'JSON':
                return $this->getJson();
                break;
            default:
                return $this->getXml();
        }
    }

    /**
     * Génère le document XML pour flux partenaire
     * @return xmlDoc
     */
    protected function getXml() {

        $url_root_img = $this->paramClient['url_root_img'] . $this->paramClient['format_photo'] . '/';
        $url_ads = ( $this->paramClient['type_flux'] > 1 ) ? $this->paramClient['url_rnt_ads'] : $this->paramClient['url_sal_ads'];

        $results = pdoquery($this->sSql);
        $dom = new DOMDocument('1.0', 'utf-8');
        $annonces = $dom->createElement('annonces');
        $dom->appendChild($annonces);

        foreach ($results as $key => $value) {
            $annonce = $dom->createElement('annonce');
            $annonces->appendChild($annonce);

            $designation = $dom->createElement('designation');
            $annonce->appendChild($designation);
            $designationData = $dom->createCDATASection($value[$this->tablePrefix . '_property_type']);
            $designation->appendChild($designationData);

            if (isset($this->paramClient['description'])) {
                $description = $dom->createElement('description');
                $annonce->appendChild($description);
                $descriptionData = $dom->createCDATASection($this->truncateDescription($value[$this->tablePrefix . '_default_text'], $this->paramClient['description']));
                $description->appendChild($descriptionData);
            }

            $price = $dom->createElement('price');
            $annonce->appendChild($price);
            $priceData = $dom->createCDATASection($value[$this->tablePrefix . '_price']);
            $price->appendChild($priceData);

            $area = $dom->createElement('area');
            $annonce->appendChild($area);
            $areaData = $dom->createCDATASection($value[$this->tablePrefix . '_area']);
            $area->appendChild($areaData);

            $nbrooms = $dom->createElement('nbrooms');
            $annonce->appendChild($nbrooms);
            $nbroomsData = $dom->createCDATASection($value[$this->tablePrefix . '_nb_rooms']);
            $nbrooms->appendChild($nbroomsData);

            $localisation = $dom->createElement('localisation');
            $annonce->appendChild($localisation);
            $localisationData = $dom->createCDATASection($value[$this->tablePrefix . '_default_town'] . ' (' . $value[$this->tablePrefix . '_post_code'] . ')');
            $localisation->appendChild($localisationData);

            $image = $dom->createElement('image', $url_root_img . substr(strtolower($value['id_annonce']), 0, 3) . "/" . strtolower($value[$this->tablePrefix . '_photo']) . '.jpg');
            $annonce->appendChild($image);

            $ads_link = str_replace('#IDADS#', strtolower($value['id_annonce']), $url_ads);
            $link = $dom->createElement('lien', $ads_link);
            $annonce->appendChild($link);
        }
        $this->xmlDoc = $dom->saveXML();
        return $this->xmlDoc;
        exit(0);
    }

    /**
     * Génère le document JSON pour flux partenaire
     * @return json
     */
    protected function getJson() {

        try {
            $xml = simplexml_load_string(utf8_encode($this->getXml()));

            foreach ($xml->children() as $annonce) {
                $json[] = array(
                    'designation' => (string) $annonce->designation,
                    'description' => (string) $annonce->description,
                    'price' => (string) $annonce->price,
                    'nbrooms' => (string) $annonce->nbrooms,
                    'localisation' => (string) $annonce->localisation,
                    'image' => (string) $annonce->image,
                    'lien' => (string) $annonce->lien,
                );
            }
            
            echo json_encode($json);
            
        } catch (Exception $e) {
            echo $e->getCode();
        }
    }

    /**
     * Génère le document CSV pour flux partenaire
     * @todo Format Excel compatible à faire
     *  
     */
    protected function getCsv() {

//        $xml = simplexml_load_string($this->getXml());
//
//        header('Content-type: text/csv');
//        header('Content-disposition: attachment;filename=flux.csv');
//
//        foreach ($xml->children() as $annonce) {
//            $tab[] = array(
//                'designation' => '"' . (string) $annonce->designation . '"',
//                'description' => '"' . (string) $annonce->description . '"',
//                'price' => '"' . (string) $annonce->price . '"',
//                'nbrooms' => '"' . (string) $annonce->nbrooms . '"',
//                'localisation' => '"' . (string) $annonce->localisation . '"',
//                'image' => '"' . (string) $annonce->image . '"',
//                'lien' => '"' . (string) $annonce->lien . '"',
//                '' => "\n",
//            );
//        }
//
//        foreach ($tab as $v) {
//            echo implode(',', $v);
//        }
    }

    /**
     * Tronçonne un texte selon une longueur souhaitée
     * @param string $longString
     * @param int $length
     * @return string
     */
    protected function truncateDescription($longString, $length = 0) {

        $separator = '...';
        $separatorLength = strlen($separator);
        $maxLength = $length - $separatorLength;
        $start = $maxLength;
        $trunc = strlen($longString) - $maxLength;

        return substr_replace($longString, $separator, $start, $trunc);
    }

    /**
     * CLEAN UP ROUTINE...
     */
    public function __destruct() {

        $this->paramClient = NULL;
        $this->tablePrefix = NULL;
        $this->xmlDoc = NULL;
        $this->sSql = NULL;
    }

}
