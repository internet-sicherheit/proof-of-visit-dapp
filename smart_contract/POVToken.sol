pragma solidity ^0.4.0;

import "browser/ERC721.sol";




//needs to implement all Functions of ERC721, alot of functions missing right now

contract POVToken is TokenManager, ERC721 {
    
    string public name = "ProofOfVisit Token";
    string public symbol = "POV-T";
    
    
     function totalSupply() public view returns (uint) {
        return povtokens.length - 1;
    }
    
    
    
    
    
  
     function ownerOf(uint256 _tokenID) external view returns (address owner){
         
         return tokenIndexToOwner[_tokenID];
  
     }
     
  
   function balanceOf(address _owner) public view returns (uint256 balance)
   {
       
       return ownershipTokenCount[_owner];
       
   }
   
   
   
   function locationNameOfToken(uint256 _tokenID) public view returns (string locationname)
   {
    
      return locations[tokenIndexToLocationIndex[_tokenID]].locationname;
  
   }
    
    
    
    
    
     // |1| Right now there is no plans to send or interchange tokens after the owner is declared. It's self-explanatory because a Token is a Proof of Visit, you can't give your "visit" to someone else
     
     //Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);
     
    //See |1|
    function  transfer(address requestaddress, uint256 _tokenId) external {
         
        
        emit Transfer(this, requestaddress, _tokenId);

    }
     
    //See |1|
    function approve(address _to, uint256 _tokenId) external
    {
        
       
        emit Approval(msg.sender, _to, _tokenId);
    }
     
    //See |1|
    function transferFrom(address _from, address _to, uint256 _tokenId) external
    {
          
       
        emit Transfer(_from, _to, _tokenId);
          
    }
    
    
   

}