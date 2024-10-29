/**
 * TopLogo画像のランダム表示
 **/

 var randomList = new Array(
  "./img/Logo1.png",
  "./img/Logo2.png",
  "./img/Logo3.png",
  "./img/Logo4.png",
  "./img/Logo5.png" );
var num = Math.floor(Math.random() * randomList.length);
var printHtml = '<img src=' + randomList[num] + ' alt="TopLogo">';
document.write(printHtml);