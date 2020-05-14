// Version of Solidity compiler this program was written for
pragma solidity ^0.5.0;

contract Add {
    
    int256 public a;
   
   constructor () public {
       a = 0;
   }
   
    function addOne () public{
        a++;
    }   

    
}