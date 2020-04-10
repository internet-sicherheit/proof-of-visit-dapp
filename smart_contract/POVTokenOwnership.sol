pragma solidity ^0.4.0;

import "browser/ERC721.sol";
import "browser/Tokenmanager.sol";


//needs to implement all Functions of ERC721, alot of functions missing right now

contract Tokenownership is Tokenmanager, ERC721{
    
    string public name = "ProofOfVisit Token";
    string public symbol = "POV-T";
    
    
     function totalSupply() public view returns (uint) {
        return povtokens.length - 1;
    }
    
     function transfer(address requester, uint256 _tokenId) external {
         
         //sets the owner of the tokenID to the requester
         tokenIndexToOwner[_tokenId] = requester;
         
     }

}