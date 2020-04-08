pragma solidity ^0.5.0;

contract web3jtest {
    
    uint256 public counter;
    
    constructor() public  {
        counter = 0;
    }
    
    function add () public {
         counter++;
    }
}