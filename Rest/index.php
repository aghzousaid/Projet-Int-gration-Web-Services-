

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
 


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <title> EGO Networks </title>
 <meta http-equiv ="content-type"
 content="text/html; CHARSET = UTF-8"/>
	<link rel="stylesheet" type="text/css" href="./base.css" media="all" />
	<link rel="stylesheet" type="text/css" href="./modele11.css" media="screen" />
	
	 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>arbor.js</title>
  <meta name="description" content="a graph visualization library using web workers and jQuery">
	<meta name="generator" content="Bluefish 2.2.7" >
	<meta name="author" content="aghzou" >
	<script type="text/javascript" src="http://use.typekit.com/mxh7kqd.js"></script>
  <script type="text/javascript">try{Typekit.load();}catch(e){}</script>
	<!--<link rel="stylesheet" href="style/site.css" type="text/css" charset="utf-8">-->
	
	<link rel="stylesheet" href="" type="text/css" charset="utf-8">
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
		require ('functions.php');
		
		?>
<h3>Choose your service:</h3>
	<?php
	//Service form composed of a choice of a service, an id of an author, k and a year.
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
	 Author id:<input type="text" name="author_id"/>

    </p>';


?>
	<?php 
	// Using global variables defined in 'default_variables.php' to limit the possibilities 
	global $kmin,$kmax,$ymin,$ymax;
  echo' 
  <p>
  K :
    <select name="client_k" size="1">';
    for($k=$kmin;$k<$kmax;$k++){
    	echo '<option>'.$k;
    }
    

   echo '
    </select>
    
 
 ';

  echo' 

  Year :
    <select name="client_y" size="1">';
    for($k=$ymin;$k<$ymax;$k++){
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
<!--
Link to the additional operations
-->
<li><a href="plus.php">>>Plus</a></li>

<h3>Results:</h3>
<h4>
<?php
//We are treating the form
	if(isset($_POST['author_id']) && !empty($_POST['author_id'])&& isset($_POST['sql_request']) && !empty($_POST['sql_request'])&&isset($_POST['client_y']) && !empty($_POST['client_y'])&&isset($_POST['client_k']) && !empty($_POST['client_k'])){

	//We are creating variables looking like '0-0' to limt the numbers of parameters to send to the RESTful service
			$k=$_POST['client_k']."-".$_POST['client_k'];	
		
			$year=$_POST['client_y']."-".$_POST['client_y'];	
		//Verification if the ids are numbers
		if (ctype_digit($_POST['author_id'])){
				//Each case value corresponds on the option value of the service, so it corresponds on each service of the RESTful web service
		//Serviceurl is a function defined in 'functions.php'
		switch ($_POST['sql_request']) {
    		case 'graph of co-authors':
    			  $arrayScript = serviceurl("networkViewer",$_POST['author_id']."/".$k."/".$year);
    				//We are using the defined separators for the construction of the array
	         global $separator1;
            global $separator2;
	         $array1=explode($separator1,$arrayScript);
  			        //for($i=0;$i<sizeof($array1)-1;$i++) {
			        $array2=explode($separator2,$array1[0]);
			
			       // for($j=0;$j<sizeof($array2);$j++) 

			  	
			  	
 echo'<?xml version="1.0" encoding="UTF-8" ?>';
echo'<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.0//EN" "http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd">';

echo'<svg xmlns="http://www.w3.org/2000/svg" version="1.0" width="673" height="744">';
 echo' <g stroke="black" stroke-width="5.3">
    <line y2="303.37296" x2="89.224754" y1="164.9425" x1="107.60757" />
    <line y2="567.65265" x2="139.80212" y1="431.3772" x1="99.288574" />
    <line y2="249.94807" x2="277.63818" y1="334.65576" x1="136.78673" />
    <line y2="107.57457" x2="519.77295" y1="183.03452" x1="390.54633" />
    <line y2="357.67343" x2="538.42572" y1="253.4962" x1="387.83307" />
    <line y2="604.09027" x2="576.23059" y1="460.46851" x1="587.3493" />
  </g>
  <g fill="white" stroke="black" stroke-width="1">
    <circle cx="117" cy="100" r="50" />
    <circle cx="80" cy="369" r="65" />
    <circle cx="159" cy="631" r="65" />
    <circle cx="334" cy="216" r="65" />
    <circle cx="577" cy="74" r="65" />
    <circle cx="593" cy="395" r="65" />
    <circle cx="571" cy="670" r="65" />
  </g>
  <g font-family="Arial" font-size="10" font-weight="100" text-anchor="middle">
  					
   		  <text x="116.24995" y="137.05055">'.$array2[0].'</text>
    		  <text x="80.582008" y="403.68304">2</text>
           <text x="158.50833" y="665.79883">3</text>
           <text x="331.87473" y="251.37074">4</text>
           <text x="574.51074" y="109.68653">5</text>
    <text x="591.93628" y="430.24881">6</text>
    <text x="573.13318" y="704.76196">7</text> 
    
  </g>
</svg>';
?>
				?>
				
				 <!-- <div id="arbor">
  <div id="nav">
    <a class="back" href="#"></a>
    <p>a graph visualization library using web workers and jQuery</p>
  </div>
  <canvas id="sitemap" width="800" height="400"></canvas>

  <div id="docs">
    <div id="introduction">
      <h2>about arbor</h2>
      <p>Arbor is a graph visualization library built with web workers and jQuery. Rather than trying to be an all-encompassing framework, arbor provides an efficient, force-directed layout algorithm plus abstractions for graph organization and screen refresh handling.</p>
      <p>It leaves the actual screen-drawing to you. This means you can use it with canvas, <b>SVG</b>, or even positioned <b>HTML</b> elements; whatever display approach is appropriate for your project and your performance needs. </p>
      <p>As a result, the code you write with it can be focused on the things that make your project unique – the graph data and your visual style – rather than spending time on the physics math that makes the layouts possible.</p>
      <p>&nbsp;</p>
      
      <h2>installation</h2>
      <p>To use the particle system, get jQuery and add the file at <code>lib/arbor.js</code>
        to your path somewhere and include them in your <b>HTML</b>:</p>
      <p class="code">
          &lt;script src="path/to/jquery.min.js"&gt;&lt;/script&gt;<br>
          &lt;script src="path/to/arbor.js"&gt;&lt;/script&gt;  
      </p>
      <p>If you want to let arbor handle realtime color and value tweens for you, 
        include the <code>arbor-tween.js</code> file as well. this will add a pair of new 
        tweening methods to the <code>ParticleSystem</code> object (see the docs to decide if 
        this appeals to you or not).</p>
      <p class="code">
          &lt;script src="path/to/jquery.min.js"&gt;&lt;/script&gt;<br>
          &lt;script src="path/to/arbor.js"&gt;&lt;/script&gt;<br>
          &lt;script src="path/to/arbor-tween.js"&gt;&lt;/script&gt;  
      </p>
    
      <h2>getting started</h2>
      <p>The source distribution contains a <a href="https://github.com/samizdatco/arbor/tree/master/docs/sample-project">sample project</a> 
      that demonstrates some of the basic idioms for working with the library to build a 
      visualization. More detailed documentation can be found in the <a href="/reference">reference</a> section.</p>
      
      <p>In addition, the demos folder of the source distribution contains standalone versions 
      of the demos on the site. But since all of them use <b>xhr</b> to fetch their data, you'll 
      still need to view them from an http server. If you don't have a copy of
      apache handy, use the <code>demo-server.sh</code> script to create a local server.</p>
      <p>&nbsp;</p>

      <h2>Contribute</h2>
      <p>Code submissions are greatly appreciated and highly encouraged. Please send
      pull requests with fixes, enhancements, etc. to <a href="https://github.com/samizdatco">samizdatco</a> 
      on github. The oldschool may also pipe their <code>diff -u</code> output to <code style="unicode-bidi:bidi-override; direction: rtl;">gro.sjrobra@ofni</code>.</p>
      <p>&nbsp;</p>

      <h2>license</h2>
      <p>Arbor is released under the <a href="http://en.wikipedia.org/wiki/MIT_License"><b>MIT</b> license</a>. 
      <p>&nbsp;</p>

      <h2>colophon</h2>
      <p>Arbor’s design is heavily influenced by Jeffrey Bernstein’s excellent 
        <a href="http://murderandcreate.com/physics/">Traer Physics</a> 
        library for <a href="http://www.processing.org">Processing</a>. In addition, 
        much of the brute-force physics code was originally adapted from Dennis Hotson’s 
        <a href="https://github.com/dhotson/springy">springy.js</a>.</p>
        <p>The
        Barnes-Hut n-body implementation is based on Tom Ventimiglia and Kevin Wayne’s vivid
        <a href="/docs/barnes-hut">description</a> of the algorithm.
        Thanks to all for such elegantly simple and comprehensible code.</p>
      
      </p>
      
    </div>
    
    <div id="reference">
      <h1>ParticleSystem</h1>
      <div class="lead-in">
        <p>The particle system stores your nodes
          and edges and handles updating their coordinates as the simulation progresses.</p>
        <a href="#" class="more">creation &amp; use…</a>
        <h2><a href="#">&times;</a> creation</h2>
        <p>Parameters for the physics simulation can be set at creation-time 
          by calling the constructor with the arguments:</p>
        <p class="code">
          arbor.ParticleSystem(repulsion, stiffness, friction, gravity, fps, dt, precision)
        </p>
        <p>The parameters and their defaults are:</p>
        <ul>
          <li><em>repulsion </em><b>1,000</b> the force repelling nodes from each other</li>
          <li><em>stiffness </em><b>600</b> the rigidity of the edges </li>
          <li><em>friction </em><b>0.5</b> the amount of damping in the system </li>
          <li><em>gravity </em><b>false</b> an additional force attracting nodes to the origin </li>
          <li><em>fps </em><b>55</b> frames per second </li>
          <li><em>dt </em><b>0.02</b> timestep to use for stepping the simulation </li>
          <li><em>precision </em><b>0.6</b> accuracy vs. speed in force calculations<br>
              <em>&nbsp;</em><b>&nbsp;</b> (zero is fast but jittery, one is smooth but cpu-intensive)</li>
        </ul>
        <p>The defaults will be used for any omitted arguments. Parameters can also be passed 
           in an object literal. For reference, the following calls are all equivalent:</p>
        <p class="code">
          arbor.ParticleSystem()<br>
          arbor.ParticleSystem(600)<br>
          arbor.ParticleSystem(600, 1000, .5, 55, .02, false)<br>
          arbor.ParticleSystem({friction:.5, stiffness:600, repulsion:1000})
        </p>
        <p>Once a particle system has been created, the parameters can be tweaked by passing
           an object to the <code>.parameters</code> method:</p>
        <p class="code">
          var sys = arbor.ParticleSystem()<br>
          sys.parameters({gravity:true, dt:0.005})
          </p>
        <h2>rendering</h2>
        <p>The particle system doesn’t do any drawing on its own; you need to provide those 
           routines in a separate object that will be triggered by the system when it’s time
           to redraw the screen. To set this up, create an object with two methods (<code>.init</code>
           and <code>.redraw</code>), then set the particle system’s <code>renderer</code>
           attribute to your new object:</p>
        <p class="code">
          var myRenderer = {<br>
            &nbsp;&nbsp;init:&nbsp;&nbsp;function(system){ console.log("starting",system) },<br>
            &nbsp;&nbsp;redraw:function(){ console.log("redraw") }<br>
          }<br>
          var sys = arbor.ParticleSystem()<br>
          sys.renderer = myRenderer
          </p>
        <p>The <code>.init</code> method will be called once before the first pass through the
           draw loop. Then the <code>.redraw</code> method will be called each time the screen
           needs to be re-plotted. Take a look at the <a href="https://github.com/samizdatco/arbor/tree/master/docs/sample-project">sample project</a> for a
           slightly more elaborated example of how this works.
           </p>
      </div>
      
      <h2>nodes</h2>
      <dl>
        <dt>addNode<em>(name, data)</em></dt>
        <dd>
          <p><code class="arg">name</code> is a string identifier that will be used in talking to the particle
          system about this node.</p>
          <p><code class="arg">data</code> is an object with keys and values set by the user. you can use it to
          store additional information about the node for later use in e.g., drawing.</p>
          <div class="desc">Creates a new node in the particle system and returns the resulting <code>Node</code> object.</div>
          <hr>
        </dd>
        
        <dt>getNode<em>(name)</em></dt>
        <dd>
          <p><code class="arg">name</code> is an identifier for a node already in the system</p>
          <div class="desc">Returns the corresponding <code>Node</code> object or 
            <code>undefined</code> if none is found. If called with a node as an 
            argument, it will return that same node (for idempotence).</div>
            <hr>
        </dd>

        <dt>pruneNode<em>(node)</em> </dt>
        <dd>
          <p><code class="arg">node</code> is either an identifier string or a Node object</p>
          <div class="desc">Removes the corresponding <code>Node</code> from the
            particle system (as well as any <code>Edge</code>s in which it is a participant).</div>
            <hr>
        </dd>
      </dl>
      <h2>edges</h2>
      <dl>
        <dt>addEdge<em>(source, target, data)</em></dt>
        <dd>
          <p><code class="arg">source</code> and <code class="arg">target</code> are either identifier strings or a Node objects.</p>
          <p><code class="arg">data</code> is a user data object with additional information about the edge.</p>
          <div class="desc">Creates a new edge connecting the source and target nodes
            then returns the resulting <code>Edge</code> object.
            </div>
            <hr>
        </dd>        
        <dt>getEdges<em>(source, target)</em> </dt>
        <dd>
          <p><code class="arg">source</code> and <code class="arg">target</code> are either identifier strings or a Node objects.</p>
          <div class="desc">Returns an array containing all <code>Edge</code> objects connecting the specified nodes.
            If no connections exist, returns <code style="letter-spacing:2px;">[]</code>.
            </div>
            <hr>
        </dd>        
        <dt>getEdgesFrom<em>(node)</em></dt>
        <dd>
          <p><code class="arg">node</code> is a string identifier or Node object</p>
          <div class="desc">Returns an array containing all <code>Edge</code> objects in which the
            node is the source.
            If no connections exist, returns <code style="letter-spacing:2px;">[]</code>.
            </div>
            <hr>
        </dd>        
        <dt>getEdgesTo<em>(node)</em> </dt>
        <dd>
          <p><code class="arg">node</code> is a string identifier or Node object</p>
          <div class="desc">Returns an array containing all <code>Edge</code> objects in which the
            node is the target.
            If no connections exist, returns <code style="letter-spacing:2px;">[]</code>.
            </div>
            <hr>
        </dd>        
        <dt>pruneEdge<em>(edge)</em> </dt>
        <dd>
          <p><code class="arg">edge</code> is an Edge object.</p>
          <div class="desc">Removes the corresponding <code>Edge</code> from the particle system.
            </div>
            <hr>
        </dd>        
      </dl>
      <h2>iteration</h2>
      <dl>
        <dt>eachNode<em>(callback)</em></dt>
        <dd>
          <p><code class="arg">callback</code> is a function with the signature ƒ(<code>node</code>, <code>pt</code>) where
            <code class="arg">node</code> is a Node object and <code class="arg">pt</code> is a Point object with its current 
            location.</p>
          <div class="desc">The callback function will be invoked once for each <code>Node</code> in the system.</div>
          <div class="desc">Note that while the <code>node.p</code> attribute is always in the coordinate system of
            the particle system, the <code>pt</code> argument is transformed into pixel coordinates
            (provided you have called <code>.screenSize</code> to specify the screen bounding box).</div>
            <hr>
        </dd>

        <dt>eachEdge<em>(callback)</em></dt>
        <dd>
          <p><code class="arg">callback</code> is a function with the signature ƒ(<code>edge</code>, <code>pt1</code>, <code>pt2</code>) where
            <code class="arg">edge</code> is an Edge object and <code class="arg">pt1</code> and <code class="arg">pt2</code> are Point objects
            with the current endpoint locations.</p>
          <div class="desc">The callback function will be invoked once for each <code>Edge</code> in the system.</div>
          <div class="desc">Similar to the behavior of <code>.eachNode</code>, the <code>edge.source.p</code> and 
            <code>edge.target.p</code> attributes are always in the coordinate system of
            the particle system, while <code>pt1</code> and <code>pt2</code> will be transformed into pixel coordinates
            (provided you have called <code>.screenSize</code> to specify the screen bounding box).</div>
            <hr>
        </dd>
      </dl>
      <h2>modification</h2>
      <dl>
        <dt>graft<em>(branch)</em></dt>
        <dd>
          <p><code class="arg">branch</code> is an object of the form <code>{nodes:{}, edges:{}}</code>.</p>
          <p>the <code class="arg">nodes</code> attribute contains a mapping of node names to data objects. For example,</p>
          <p class="code">{ nodes:{foo:{color:"red", mass:2},<br> 
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;bar:{color:"green"}} }</p>
          <p style="text-indent:-10px">the <code class="arg">edges</code> attribute contains nested objects to map source identifier to target, then 
            target to edge data object. e.g,</p>
          <p class="code">{ edges:{bar:{foo:{similarity:0},<br> 
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;baz:{similarity:.666}} }</p>
                        
          <div class="desc">Adds nodes and edges to the current set in the particle system. The
            leaf object values in the <code>branch</code> argument will be accessible through the 
            <code>.data</code> attribute of the resulting <code>Node</code>s and <code>Edge</code>s.
            </div>
            <hr>
        </dd>        
        
        <dt>merge<em>(branch)</em></dt>
        <dd>
          <p><code class="arg">branch</code> is an object of the form <code>{nodes:{}, edges:{}}</code>
            (see <code>.graft</code> for details).</p>
          <div class="desc">Adds nodes and edges to the current set in the particle system and removes
            any that are not present in the new branch. Conserved nodes will maintain their position
            and state.
            </div>
            <hr>
        </dd>        

        <dt>prune<em>(callback)</em></dt>
        <dd>
          <p><code class="arg">callback</code> is a function with the signature ƒ(<code>node</code>, <code>from</code>, <code>to</code>) where
            <code class="arg">node</code> is a Node object and <code class="arg">from</code> and <code class="arg">to</code> are arrays
            of edges for which node is the source and target respectively.</p>
          <div class="desc">The callback function will be invoked once for each <code>Node</code> 
            in the system and should return <code>true</code> if the node should be pruned or do 
            nothing if the node should remain unaltered. Note that pruning a node will also remove all edges in 
            which it participates</div>
            <hr>
        </dd>
      </dl>
      <h2>system settings</h2>
      <dl>
        <dt>parameters<em>( ) or (params)</em></dt>
        <dd>
          <p>if present, <code class="arg">params</code> is an object containing new settings values
             for the particle system. Valid keys are the same as for the <code>ParticleSystem</code>
             constructor function:
             <code>repulsion</code>, <code>stiffness</code>, <code>friction</code>, <code>gravity</code>, <code>fps</code>, and <code>dt</code>.
            </p>
          <div class="desc">If called with no arguments, returns an object with the current system
            parameters as keys and values. If an argument is supplied, any values specified will
            be used as the new parameters (omitted values will remain unchanged).
          </div>
            <hr>
        </dd>
        
        <dt>fps<em>( ) or (fps)</em></dt>
        <dd>
          <p>if present, the <code class="arg">fps</code> argument is a positive integer. 
            </p>
          <div class="desc">If called with no arguments, returns the frame rate achieved over
            the last few seconds of drawing. Otherwise the argument will set the new target
            frame rate. This affects the frequency with which the particle system iterates 
            its simulation as well as the frequency with which the <code>ParticleSystem</code> calls the 
            <code>.redraw</code> method of the object pointed to by its 
            <code>.renderer</code> attribute.
          </div>
            <hr>
        </dd>
        <dt>bounds<em>( )</em></dt>
        <dd>
          <div class="desc">Returns a bounding box containing all nodes using system
            coordinates. The return value is of the form:
          </div>
          <p class="code">{ topleft:{x:, y:}, bottomright:{x:, y:} }</p>
            <hr>
        </dd>
        
        <dt>energy<em>( )</em></dt>
        <dd>
          <div class="desc">Returns some basic stats on the state of activity in the
            system. The values are in terms of velocity within the system’s coordinate
            frame. This can be a useful measure of when the layout has stabilized. 
            The return value is of the form:
          </div>
          <p class="code">{sum:, max:, mean:, n:}</p>
            <hr>
        </dd>
        <dt>start<em>( )</em></dt>
        <dd>
          <div class="desc">Manually start the system running. By default the system
            will run and pause on its own based on the level of energy in the particles.
            You should only need to manually start after having previously called the <code>.stop</code> method.
          </div>
          <hr>
        </dd>
        <dt>stop<em>( )</em></dt>
        <dd>
          <div class="desc">Pauses the particle simulation until <code>.start</code> is called.
            Since the system begins running as soon as it is supplied with nodes and edges, you
            may wish to call <code>.stop</code> shortly after creating the system object if it 
            will not be displayed until later in the page lifetime (e.g., until a user action takes place).
          </div>
          <hr>
        </dd>
      </dl>
      <h2>coordinate helpers</h2>
      <dl>
        <dt>screenSize<em>(width, height)</em></dt>
        <dd>
          <p><code class="arg">width</code> and <code class="arg">height</code> are positive integers defining the 
            dimensions of the screen area you will be drawing in.</p>
          <div class="desc">Calling this method enables automatic coordinate transformations
            from the particle system’s coordinate system to your display’s. This can be seen
            in the points supplied by the <code>.eachNode</code> and <code>.eachEdge</code> 
            iterators as well as the <code>to</code>/<code>fromScreen</code> and <code>nearest</code> 
            methods in this section.
          </div>
          <div class="desc">You will nearly always want to call this once when setting up your
            <code>ParticleSystem</code> and <code>renderer</code> as well as whenever the dimensions
            of the display area change.
          </div>
          <hr>
        </dd>
        
        <dt>screenPadding<em>(top, right, bottom, left)</em></dt>
        <dd>
          <p>All arguments are integers defining the number of pixels that should be left 
            blank along each edge of the display area. Either 1, 2, or 4 arguments are
            expected and are interpreted similarly to the CSS <code>padding:</code> property.</p>
          <div class="desc">When the system transforms points
            between coordinate systems it will factor the padding into the locations it
            provides to <code>.eachNode</code> and company.
          </div>
          <hr>
        </dd>

        <dt>screenStep<em>(stepsize)</em></dt>
        <dd>
          <p><code class="arg">stepsize</code> is a number between 0 and 1 defining the
            amount the bounding box should move from one frame to the next.</p>
          <div class="desc">As the nodes move and the bounding box changes, the system
            applies a variable amount of smoothing to the ‘camera’ movements. As stepsize
            approaches 1 the amount of smoothing decreases as the bounds updates become more
            instantaneous.
          </div>
          <hr>
        </dd>
        
        <dt>screen<em>() or (opts)</em></dt>
        <dd>
          <p>If present, <code class="arg">opts</code> is an object of the form:</p>
          <p class="code">{ size:{width:400, height:300},<br> 
            &nbsp;&nbsp;padding:[1,2,3,4],<br>
            &nbsp;&nbsp;step:.1 }</p>
          <div class="desc">This is a shorthand method combining the functions of the prior three. 
            If called without an argument, returns the current screen size/padding/scaling. If an 
            argument is supplied, updates the settings accordingly.
          </div>
          <hr>
        </dd>

        <dt>toScreen<em>(systemPoint)</em> </dt>
        <dd>
          <p><code class="arg">systemPoint</code> is a <code>Point</code> object whose x and y
            values are set using the system’s internal coordinate scheme.</p>
          <div class="desc">Converts the x and y to screen coordinates on the basis of the current
            screen size and padding, returning the result as a new <code>Point object</code>. If the
            size hasn’t been set or the system hasn’t started yet, <code>undefined</code> will be returned
            instead.
          </div>
          <hr>
        </dd>
        <dt>fromScreen<em>(screenPoint)</em> </dt>
        <dd>
          <p><code class="arg">screenPoint</code> is a <code>Point</code> object whose x and y
            values are using the screen’s pixel coordinates.</p>
          <div class="desc">Converts the x and y to system coordinates on the basis of the current
            screen size and padding, returning the result as a new <code>Point object</code>. If the
            size hasn’t been set or the system hasn’t started yet, <code>undefined</code> will be returned
            instead.
          </div>
          <hr>
        </dd>
        <dt>nearest<em>(screenPoint)</em></dt>
        <dd>
          <p><code class="arg">screenPoint</code> is a <code>Point</code> object whose x and y
            values are using the screen’s pixel coordinates.</p>
          <div class="desc">Returns a reference to the node nearest the argument’s screen position
            in an object of the form:
          </div>
          <p class="code">{node:, point:, distance:}</p>
          <div class="desc"><code>node</code> and <code>point</code> will either be the eponymous
            objects or <code>null</code> depending on whether any such node exists. <code>distance</code>
            is measured in pixels.
          </div>
          <hr>
        </dd>
        
        
      </dl>
      <h2>tweening</h2>
      <dl>
        <dt>tweenNode<em>(node, duration, opts)</em></dt>
        <dd>
          <p><code class="arg">node</code> is a <code>Node</code> object or an identifier string
            for the node whose values you wish to tween.</p>
          <p><code class="arg">duration</code> is the time (in seconds) the transition should last.</p>
          <p><code class="arg">opts</code> is a mapping of names and target values.</p>
          <div class="desc">
            This method allows you to initiate gradual transitions of values in the <code>.data</code>
            object of a given <code>Node</code>. For instance consider a node whose <code>.data</code> 
            object looks like:
          </div>
          <p class="code">{color:"#00ff00", radius:1}</p>
          <div class="desc">
            The following call will make this node quadruple in size and change color to light blue 
            over the course of 3 seconds:
          </div>
          <p class="code">sys.tweenNode(myNode, 3, {color:"cyan", radius:4})</p>
          
          <div class="desc">
            The system can handle tweening numerical values or colors which are expressed as either 
            named <b>CSS</b> colors or hex strings beginning with a “<code>#</code>”.
          </div>
          <div class="desc">
            There is also a pair of ‘magic’ keys that can be included in the <code class="arg">opts</code> 
            argument to modify the tween behavior. <code>delay</code> specifies the time
            in seconds before the tween should begin. <code>ease</code> can be set to
            one of the names seen in the <code>src/easing.js</code> file to control the dynamics
            of the transition. 
          </div>
          <div class="desc">
            <div class="bang">!</div>
            Note that the tween methods are disabled unless you include the <code>arbor-tween.js</code>
            file in your page. 
          </div>
          <hr>
        </dd>
        
        <dt>tweenEdge<em>(edge, duration, opts)</em></dt>
        <dd>
          <p><code class="arg">edge</code> is the <code>Edge</code> whose values you wish
            to tween.</p>
          <p><code class="arg">duration</code> is the time (in seconds) the transition should last.</p>
          <p><code class="arg">opts</code> is a mapping of names and target values.</p>
          <div class="desc">
            Identical in behavior to <code>.tweenNode</code> except that it operates on the <code>.data</code>
            attribute of an <code>Edge</code> instead of a <code>Node</code>. 
          </div>
          <div class="desc">
            <div class="bang">!</div>
            Note that the tween methods are disabled unless you include the <code>arbor-tween.js</code>
            file in your page. 
          </div>
          <hr>
        </dd>
      </dl>    
            
            
      <div class="sect">datastructures</div>
            
      <h1>Node</h1>
      <div class="lead-in">
        <p>Node objects encapsulate the current physics state of a point in the particle
          system as well as giving you a place to attach associated non-physics metadata.</p>
        <a href="#" class="more">creation &amp; use…</a>
        <h2><a href="#">&times;</a> creation</h2>
        <p>New nodes are created through the particle system’s <code>.addNode</code> method. For
          example:</p>
        <p class="code">
          sys = arbor.ParticleSystem()<br>
          node = sys.addNode("mynode", {mass:2, myColor:"goldenrod"})
        </p>
        <p>This will create a new <code>Node</code> object with a <code>.data</code> field
          containing <code>{myColor:"goldenrod"}</code>. Note that the mass value was stripped
          out of the data object and used for the node’s mass in the simulation. </p>
        <p style="margin-top:1em;">The ‘magic’
          variables you can use in this way (and their defaults):
        </p>
        <ul>
          <li><em style="width:30px;">mass </em><b>1.0</b> the node’s resistance to movement and repulsive power</li>
          <li><em style="width:30px;">fixed </em><b>false</b> if true, the node will be unaffected by other particles</li>
          <li><em style="width:30px;">x </em><b>auto</b> the starting x position (in system coordinates)</li>
          <li><em style="width:30px;">y </em><b>auto</b> the starting y position (in system coordinates)</li>
        </ul>
        <h2>using nodes</h2>
        <p>With each tick of the simulation the values in <code>.p</code> will be updated based on
          the repulsion and spring forces in the system. To alter the node’s properties (in response 
          to, say, a mouse click), simply reset its values and the system will use the new values
          in its next tick:</p>
        <p class="code">
          console.log( node.p.x, node.p.y )<br>
          >> 1.2, 0.4<br>
          <br>
          node.p = arbor.Point(1, 1)<br>
          console.log( node.p.x, node.p.y )<br>
          >> 1, 1<br>
          <br>
          node.p.y = 13<br>
          console.log( node.p.x, node.p.y )<br>
          >> 1, 13<br>
          
          </p>
        <p style="margin-top:1em;">Each node contains an attribute called <code>.data</code> whose contents
            and use are entirely up to you. Typically it is used for storing metadata about
            the node so your rendering code can know how to draw it, what its label text
            should be, which url to go to on click, etc.</p>
           <!-- <hr>
      </div>
      <h2>system values</h2>
      <dl class="datastructure">
        <dt>name</dt><dd>String (read only)</dd><br>
        <dt>mass</dt><dd>Number</dd><br>
        <dt>fixed</dt><dd>Boolean</dd><br>
        <dt>p</dt><dd>Point</dd>
      </dl>
      <h2>user values</h2>
      <dl class="datastructure">
        <dt>data</dt><dd><code>{ … }</code></dd>
      </dl>


      <h1>Edge</h1>
      <div class="lead-in">
        <p>Edge objects hold references to the source and target nodes they connect and have
          a preferred ‘resting’ length. They will apply forces on their endpoint nodes in an
          attempt to attain this optimal distance.</p>
        <a href="#" class="more">creation &amp; use…</a>
        <h2><a href="#">&times;</a> creation</h2>
        <p>New edges are created through the particle system’s <code>.addEdge</code> method. For
          example:</p>
        <p class="code">
          sys = arbor.ParticleSystem()<br>
          node1 = sys.addNode("one node")<br>
          node2 = sys.addNode("another")<br>
          edge = sys.addEdge(node1, node2, {length:.75, pointSize:3})
        </p>
        <p>This creates a pair of <code>Node</code> objects then creates an <code>Edge</code> from
          the first to the second. The <code>length</code> key is a special variable that will be
          used for setting the edge’s resting length. Any other keys in the object passed to 
          <code>.addEdge</code> will be placed in the resulting <code>Edge</code>’s <code>.data</code>
          attribute.</p>
        <p style="margin-top:1em;">Note that <code>.addEdge</code> can be called with either actual
          <code>Node</code> objects or simply their <code>.name</code>s as arguments. If a name is
          used but a node with that identifier does not yet exist, the system will automatically create
          one before creating the edge. For instance, the above code could be simplified to:
        </p>  
        <p class="code">
          sys = arbor.ParticleSystem()<br>
          edge = sys.addEdge("one node", "another", {length:.75, pointSize:3})
        </p>

      </div>
      <h2>system values</h2>
      <dl class="datastructure">
        <dt>source</dt><dd>Node</dd><br>
        <dt>target</dt><dd>Node</dd><br>
        <dt>length</dt><dd>Number</dd>
      </dl>
      <h2>user values</h2>
      <dl class="datastructure">
        <dt>data</dt><dd><code>{ … }</code></dd>
      </dl>


      <h1>Point</h1>
      <div class="lead-in">
        <p>Point objects are simple containers for x/y coordinates bundled together
          with handy methods for doing vector calculations. Create points by calling
          <code>arbor.Point(x, y)</code>.</p>
      </div>
      <h2>coordinate data</h2>
      <dl class="datastructure">
        <dt>x</dt><dd>Number</dd><br>
        <dt>y</dt><dd>Number</dd>
      </dl>

      <h2>vector math</h2>
      <dl>
        <dt>add<em>(pt) &rarr; Point</em></dt>
        <dd>Returns a new Point with the sum of the two points.</dd>
        <dt>subtract<em>(pt) &rarr; Point</em></dt>
        <dd>Returns a new Point with the difference of the two points.</dd>
        <dt>multiply<em>(n) &rarr; Point</em></dt>
        <dd>Returns a linearly scaled copy of the original point.</dd>
        <dt>divide<em>(n) &rarr; Point</em></dt>
        <dd>Returns a linearly scaled copy of the original point.</dd>
        <dt>magnitude<em>( ) &rarr; Number</em></dt>
        <dd>Returns the point’s distance from the origin.</dd>
        <dt>normal<em>( ) &rarr; Point</em></dt>
        <dd>Returns the point’s vector normal.</dd>
        <dt>normalize<em>( ) &rarr; Point</em></dt>
        <dd>Returns a scaled copy of the point with a magnitude of one.</dd>
      </dl>
      <h2>sanity checking</h2>
      <dl>
        <dt>exploded<em>( ) &rarr; Boolean</em></dt>
        <dd>Returns <code>true</code> if x or y is NaN.</dd>
      </dl>
    </div>

  </div>

 <script src="lib/jquery-1.6.1.min.js"></script>
  <script src="lib/jquery.address-1.4.min.js"></script>

  <script src="lib/arbor.js"></script>
  <script src="lib/arbor-tween.js"></script>
  <script src="lib/graphics.js"></script>	-->						
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/graphdracula/1.0.3/dracula.min.js">
				

var g = new Dracula.Graph();
 
g.addEdge("strawberry", "cherry");
g.addEdge("strawberry", "apple");
g.addEdge("strawberry", "tomato");
 
g.addEdge("tomato", "apple");
g.addEdge("tomato", "kiwi");
 
g.addEdge("cherry", "apple");
g.addEdge("cherry", "kiwi");
 
var layouter = new Dracula.Layout.Spring(g);
layouter.layout();
 
var renderer = new Dracula.Renderer.Raphael('canvas', g, 400, 300);
renderer.draw();
				
				</script>
	 			
         <?php
         		//}
	 			//}
         break;
    		case 'year apparition':
    			serviceurl("YearApparition",$_POST['author_id']);
         break;
        	case 'belongs to giant component':
        		serviceurl("GiantComponent",$_POST['author_id']);
      
        	break;
        	case 'betweenness centrality':
        		serviceurl("BetweennessCentrality",$_POST['author_id']);
        	
         break;
       	case 'eigenvector centrality':
				serviceurl("EigenvectorCentrality",$_POST['author_id']."/".$k."/".$year);
		
   		break;
        	case 'number of authors per paper':
				serviceurl("NumberOfAuthorPerPaper",$_POST['author_id']);
         break;
        	case 'effective size':
        		serviceurl("EffectiveSize",$_POST['author_id']."/".$year);
        	
 			break;
        	case'graph of co-authors(xml)':
				serviceurl("networkViewerXML",$_POST['author_id']."/".$k."/".$year);
   		break;
        	case 'betweenness':
        		serviceurl("Betweenness",$_POST['author_id']."/".$k."/".$year);
        	break;
        	case 'power law':
        		serviceurl("PowerLaw",$_POST['author_id']."/".$k."/".$year);
       
  	      break;
    	   case 'density':
        		serviceurl("Density",$_POST['author_id']."/".$k."/".$year);
			
         break;
        	case 'global clustering':
        		serviceurl("GlobalClustering",$_POST['author_id']."/".$k."/".$year);
         break;
         case 'number of publications':	
  		      serviceurl("NumberOfPublications",$_POST['author_id']."/".$year);
   
        	break;
        	case 'Kmax':
        		serviceurl("Kmax",$_POST['author_id']."/".$year);
         break;
         case 'degree centrality':
  	      	serviceurl("DegreeCentrality",$_POST['author_id']."/".$k."/".$year);
     
         break;

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
  