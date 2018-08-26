(function() {
  // Initialize Firebase
  var config = {
    apiKey: "AIzaSyDtLoPKXk-aLG5v6oYeK53BQs3jkUD8eTs",
    authDomain: "vodafonedemo-5b4ca.firebaseapp.com",
    databaseURL: "https://vodafonedemo-5b4ca.firebaseio.com",
    projectId: "vodafonedemo-5b4ca",
    storageBucket: "vodafonedemo-5b4ca.appspot.com",
    messagingSenderId: "703637547804"
  };
  firebase.initializeApp(config);

  // Get elements
  const preObject = document.getElementById('google');

  // Create reference
  const dbRefObject = firebase.database().ref().child('Faasoos');

  var ref = firebase.database().ref();

  // Sync object changes
/*  dbRefObject.on('value', snap => {
    preObject.innerText = snap.val();
    changePenis(preObject);
    for (var i = 0; i < datasetBarChart.length; i++) {
      if (datasetBarChart[i].group === "Faasos" && datasetBarChart[i].category=="August") {
        datasetBarChart[i].measure+=1;
        console.log(datasetBarChart[i].measure);
        break;
  }
}
  });

}());*/

firebase.database().ref('brand').on('value', function(snapshot) {
                var brand = snapshot.val();
                var color;
                if(brand==null){
                  brand=6140;
                }
                for(var i = 0; i < datasetBarChart.length; i++) {
                  if (datasetBarChart[i].group === "Faasos" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Lakme" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Prime" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Pizza Hut" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Movie" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Miscellaneous" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetBarChart[i].group === "Myntra" && datasetBarChart[i].category=="August") {
                    datasetBarChart[i].measure+=1;
                    console.log(datasetBarChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }
                }
                for(var i = 0; i < datasetLineChart.length; i++) {
                  if (datasetLineChart[i].group === "Faasos" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Lakme" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Prime" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Pizza Hut" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Movie" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Miscellaneous" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }else if (datasetLineChart[i].group === "Myntra" && datasetLineChart[i].category=="August") {
                    datasetLineChart[i].measure+=1;
                    console.log(datasetLineChart[i].measure);
                    console.log(brand);
                  //  datasetBarChart[i].measure+=brand;
                    if(!color){
                      color = d3.scale.category20()
                    }
                  //  updateBarChart(datasetBarChart[i].category, color(datasetBarChart[i]));
            				//updateLineChart(datasetBarChart[i].category, color(datasetBarChart[i]));
                    break;
                  }
                }
              });
}());
