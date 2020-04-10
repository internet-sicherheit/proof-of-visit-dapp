pragma solidity ^0.4.0;


import "browser/POVTokenOwnership.sol";
import "browser/Tokenmanager.sol";


contract CreateLocation is Tokenmanager{
    
    
  //creates a new location that can give out tokens to visitors, e.g. "IFIS-Token, Institut für Internetsicherheit"
  function createLocation(string _tokenname, string _tokensymbol, string _locationname, address _locationWalletAddress ) public{
      
      Location memory _newLocation = Location({
          
          tokenname: _tokenname,
          tokensymbol: _tokensymbol,
          locationname: _locationname,
          locationaddress: _locationWalletAddress
         
  });
          
    uint256 newLocationID = locations.push(_newLocation) -1;
      
  }



//Generates a Token for a Location and sends it to requester, gets called when Visitor wants a Token from Admin
function requestToken(string _locationname,  address _locationaddress, address _requestaddress){
    
    PovToken memory _newpovtoken = PovToken({
       
        locationname: _locationname,
        locationaddress: _locationaddress
        
        
    });
    
    //safes token to array
  uint256 newTokenId = povtokens.push(_newpovtoken) -1;
  
  
  //Function from POVTokenOwnership, error atm need to accress POVTokenOwnership, working on it
    transfer(_requestaddress, newTokenId);
  
  
}

}