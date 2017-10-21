<?php
//We are using the default variables defines in the external file 'default_variables.php'
		require('default_variables.php');
		?>


		<?php
		//Here we are setting the execution limit time to 0, so there is no limit to the execution time of the php file
		set_time_limit(0);
		global $time;
//Here we are setting the execution limit time for the file_get_contents request
		ini_set('default_socket_timeout', $time);
		
	//serviceurl is a function used to run the service (by url) and getting back the result
	//$name corresponds to the name of the service to run 
	//$value corresponds to the parameters of the service
    function serviceurl($name,$value) {
    	//$durl is a defined default variable 
    	global $durl;
    	$url=$durl.$name."/".$value."";
		//We are getting the body content of the result of the RESTful webservice
    	$body = file_get_contents($url);
    	//We are treating the result
		resultArray($body);
		return $body;
		}
		
	//serviceurl2 is a developped version of the function serviceurl, the goal is the same
	//Additional features: A loop to execute the function for many author ids
	//$name corresponds to the name of the service to run 
	//$value1 corresponds to the initial author id
	//$value1 corresponds to the final author id
    function serviceurl2($name,$value1,$value2,$value3) {
    	for($value=$value1;$value<$value2+1;$value++) {
    	$url="http://localhost:8081/com.ego.test/rest/framework/".$name."/".$value."/".$value3."";
    	$body = file_get_contents($url);
		resultArray($body);
		javaScript($body);
		}
		}

//resultArray is a function permitting to adapt the result into a array
//$result corresponds on the result of the service request
function resultArray($result){
	//We are using the defined separators for the construction of the array
	global $separator1;
global $separator2;
	$array1=explode($separator1,$result);
					echo'<table>';
				
	for($i=0;$i<sizeof($array1)-1;$i++) {
			$array2=explode($separator2,$array1[$i]);
	echo '<tr>';
			for($j=0;$j<sizeof($array2);$j++) {
				echo '<td>'.$array2[$j].'</td>';
							
			}
	echo '</tr>';
	}
	
		echo '</table>';
}


function javaScript($result){
	//We are using the defined separators for the construction of the array
	global $separator1;
global $separator2;
	$array1=explode($separator1,$result);
				
	for($i=0;$i<sizeof($array1)-1;$i++) {
			$array2=explode($separator2,$array1[$i]);

			for($j=0;$j<sizeof($array2);$j++) {
			
		   ?>
				
 
							
			<?php				
			}
	}
	
}

?>