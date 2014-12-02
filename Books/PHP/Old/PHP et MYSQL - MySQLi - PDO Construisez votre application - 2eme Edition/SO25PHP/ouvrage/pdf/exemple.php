<?php
define('FPDF_FONTPATH','font/');
require('fpdf.php');


$pdf=new FPDF();
$pdf->AddPage();
$pdf->Ln(30);              //Saut de ligne
$pdf->SetFont('times','B',16);
$pdf->Cell(0,5,'EDITIONS ENI',0,1,"C");
$pdf->Output();
?> 