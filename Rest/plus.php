<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <title> EGO Networks </title>
 <meta http-equiv ="content-type"
 content="text/html; CHARSET = UTF-8"/>
	<link rel="stylesheet" type="text/css" href="./base.css" media="all" />
	<link rel="stylesheet" type="text/css" href="./modele11.css" media="screen" />
 <style type="text/css">	
h1 {
text-align: center;
font-size: 50px;	
	font-family: Cambria,Georgia,serif;}	
	
	h3{
font-size: 20px;	
	font-family: Cambria,Georgia,serif;}
	  p{
  text-align: center;
  font-size:20px;
  color: black;
  font-family: Cambria,Georgia,serif;
  }
  	  h4{
 
  font-size:15px;
  color: black;
  font-family: Cambria,Georgia,serif;
  }
table {
 border-collapse:collapse;
 width:90%;
 }
td {
 border:1px solid black;
 width:20%;
 text-align:center;
 }
 </style>	
</head>
<body>
<div id="global">

	<div id="entete">
	<img alt="" height="83" width="73" src="./images/ego_logo.png" />
		<h1>
		EGO SERVICE
		</h1>
	</div>

	<div id="centre">
	<div id="centre-bis">

		<div id="navigation">
			<ul>
				<li><a href="index.php">Home</a></li>
			</ul>
		</div>

	
		<div id="principal">

<!-- Including the functions of the external file 'functions.php'-->
<?php
require('functions.php');

?>

<h3>Choose your service:</h3>
	<?php
	//Using global variables defined in 'default_variables.php'
	global $kimin,$kimax,$kfmin,$kfmax,$yimin,$yimax,$yfmin,$yfmax;
	//Service form composed of a choice of a service, an initial and final id of an author, an initial and a final k and an initial and a final year.
	// Each option corresponds on a service of the RESTful webservice
  echo'  <form method="post" action="">
  <p>
  Service:
    <select name="sql_request" size="1">
    <option>graph of co-authors
    <option>year apparition
	 <option>belongs to giant component
	 <option>betweenness centrality
	 <option>eigenvector centrality
	 <option>number of authors per paper
	 <option>effective size
	 <option>graph of co-authors(xml)
	 <option>betweenness
	 <option>power law
	 <option>density
	 <option>global clustering
	 <option>number of publications
	 <option>Kmax
	 <option>degree centrality
    </select>
    </p>
	 Author id initial:
	 <input type="text" name="author_i"/>

     Author id final:
		<input type="text" name="author_f"/>
 </p> 
  <p>
  K initial:
    <select name="client_ki" size="1">';
    //Using default variables to limit the possibilities
    for($k=$kimin;$k<$kimax;$k++){
    	echo '<option>'.$k;
    }
    

   echo '
    </select>
     K final:
    <select name="client_kf" size="1">';
    for($k=$kfmin;$k<$kfmax;$k++){
    	echo '<option>'.$k;
    }
    

   echo '
    </select>
 </p>
 ';
  echo' 
<p>
  Year initial:
    <select name="client_yi" size="1">';
    for($k=$yimin;$k<$yimax;$k++){
    	echo '<option>'.$k;
    }
    

    echo '
     </select>
      Year final:
    <select name="client_yf" size="1">';
    for($k=$yfmin;$k<$yfmax;$k++){
    	echo '<option>'.$k;
    }
    

    echo '
     </select>
     </p>
     <p>
    <input type="submit" name="Submit" value="Submit"/>
    </p>
    </form>';

?>


<h3>Results:</h3>
<h4>
<?php
//Treating the form 
	if(isset($_POST['author_i']) && !empty($_POST['author_i'])&&isset($_POST['author_f']) && !empty($_POST['author_f'])&& isset($_POST['sql_request']) && !empty($_POST['sql_request'])&&isset($_POST['client_yi']) && !empty($_POST['client_yi'])&&isset($_POST['client_yf']) && !empty($_POST['client_yf'])&&isset($_POST['client_ki']) && !empty($_POST['client_ki'])&&isset($_POST['client_kf']) && !empty($_POST['client_kf'])){

		//We are creating variables looking like '0-0' to limt the numbers of parameters to send to the RESTful service
			$k=$_POST['client_ki']."-".$_POST['client_kf'];	
			$year=$_POST['client_yi']."-".$_POST['client_yf'];	
			
		//Verification if the ids are numbers
		if (ctype_digit($_POST['author_i'])&&ctype_digit($_POST['author_f'])){
			
			$authori=(int)$_POST['author_i'];
			$authorf=(int)$_POST['author_f'];
	//Verification if the ids are in the range
			if($authori>$authorimin&&$authori<$authorimax&&$authorf>$authorfmin&&$authorf<$authorfmax) {
	//Each case value corresponds on the option value of the service, so it corresponds on each service of the RESTful web service
	//Serviceurl2 is a function defined in 'functions.php'
		switch ($_POST['sql_request']) {
    		case 'graph of co-authors':
    			serviceurl2("networkViewer",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
    
         break;
    		case 'year apparition':
    			serviceurl2("YearApparition",$_POST['author_i'],$_POST['author_f'],"");
         break;
        	case 'belongs to giant component':
        		serviceurl2("GiantComponent",$_POST['author_i'],$_POST['author_f'],"");
      
        	break;
        	case 'betweenness centrality':
        		serviceurl2("BetweennessCentrality",$_POST['author_i'],$_POST['author_f'],"");
        	
         break;
       	case 'eigenvector centrality':
				serviceurl2("EigenvectorCentrality",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
		
   		break;
        	case 'number of authors per paper':
				serviceurl2("NumberOfAuthorPerPaper",$_POST['author_i'],$_POST['author_f'],"");
         break;
        	case 'effective size':
        		serviceurl2("EffectiveSize",$_POST['author_i'],$_POST['author_f'],$year);
        	
 			break;
        	case'graph of co-authors(xml)':
				serviceurl2("networkViewerXML",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
   		break;
        	case 'betweenness':
        		serviceurl2("Betweenness",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
        	break;
        	case 'power law':
        		serviceurl2("PowerLaw",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
       
  	      break;
    	   case 'density':
        		serviceurl2("Density",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
			
         break;
        	case 'global clustering':
        		serviceurl2("GlobalClustering",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
         break;
         case 'number of publications':	
  		      serviceurl2("NumberOfPublications",$_POST['author_i'],$_POST['author_f'],$year);
   
        	break;
        	case 'Kmax':
        		serviceurl2("Kmax",$_POST['author_i'],$_POST['author_f'],$year);
         break;
         case 'degree centrality':
  	      	serviceurl2("DegreeCentrality",$_POST['author_i'],$_POST['author_f'],$k."/".$year);
     
         break;

		}
		}

		}
		
		}


?>
</h4>
		</div>

	</div>
	</div>

</div>

<div id="footer"></div>
</body>
</html>      
  