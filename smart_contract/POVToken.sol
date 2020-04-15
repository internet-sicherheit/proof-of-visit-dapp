pragma solidity ^0.4.0;

import "browser/ERC721.sol";

import "browser/CreateLocationOrToken.sol";


//needs to implement all Functions of ERC721, alot of functions missing right now

contract POVToken is CreateLocation, ERC721 {
    
    string public name = "ProofOfVisit Token";
    string public symbol = "POV-T";
    
    
     function totalSupply() public view returns (uint) {
       // return povtokens.length - 1;
    }
    
    
    //Will maybe not be used at all because you can't send tokens to other users, but may be used for features e.g. for 10 Tokens you can get a coffee at the location
     function  transfer(address requestaddress, uint256 _tokenId) external {
         
         //sets the owner of the tokenID to the requester
        
         
     }

}