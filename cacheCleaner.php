<?php
/**
 * BETA -
 * @author christophe.egea
 *
*/
class cacheCleaner
{
    public $result = array();

    private $cacheDir;

    // 1 day in seconds 864000
    // threshold in seconds
    private $threshold = NULL;
    private $time = NULL;

    public function __construct($threshold)
    {
        $this->threshold = $threshold;
        $this->cacheDir = JSON_CACHE_DIR;
// die(var_dump( $this->cacheDir));
        if ( $files = scandir($this->cacheDir,SCANDIR_SORT_ASCENDING) ) {
            $files = array_diff( $files, array('.', '..') );
            $this->time = time();
            $i = 0;
            	
            foreach( $files as $file ) {
                $i++;
                $stat = stat($file);
                $file = $this->cacheDir . $file;
                $age = $this->time - $stat['mtime'];

                if ( $age > $this->threshold ) {
                    chmod($file,0755);
                    unlink($file);
                    $this->result['deleted_files'] .= $file . '->deleted';
                    $this->result['deleted_files_count'] = $i;
                    $i++;
                }
            }
        }
        else {
            $this->result['deleted_files_count'] = 0;
            $this->result['error_message'] = 'path could not be found : ' . $this->cacheDir;
        }
        //die(var_dump( $this->result));
        return $this->result;
    }
}
