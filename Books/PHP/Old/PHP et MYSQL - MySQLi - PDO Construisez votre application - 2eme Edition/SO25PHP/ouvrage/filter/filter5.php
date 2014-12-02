<?php
$chaine = '<script type="text/javascript">alert("Editions ENI");</script>'; 

echo filter_var($chaine, FILTER_SANITIZE_STRING, FILTER_FLAG_NO_ENCODE_QUOTES);
   
?> 