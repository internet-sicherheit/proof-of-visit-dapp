pragma solidity ^0.4.0;

    contract Manager{

    //Struct for a Location that wants to give out POV Tokens
    struct Location{
        
        string tokenname;
        string tokensymbol;
        string locationname;
        address locationaddress;
        uint256 locationID;
        
    }
    

    //Struct data for all POV-Tokens in existence
    struct PovToken{
       
        string locationname;
        address locationaddress;
        uint256 locationID;
  
    }


    //Storage for all Locations 
     Location[] locations ;
    
    //Storage for all tokens from all locations in existence
     PovToken [] povtokens;


   
   //hashmaps
      
    //This mapping represents which Index of the povtokens map is owned by who (address)
    //ID is basically the same as the INDEX!! (as far as i understand)
    mapping (uint256 => address) public tokenIndexToOwner;
    

   
   //this mapping represents which location an token has
    mapping(uint256 => uint256) public tokenIndexToLocationIndex;
    
    //used to see the total tokens of an address (needed for ERC721)
    mapping (address => uint256) ownershipTokenCount;
    

    //creates a new location that can give out tokens to visitors, e.g. "IFIS-Token, Institut für Internetsicherheit"
    
    function createLocation(string _tokenname, string _tokensymbol, string _locationname, address _locationWalletAddress ) internal returns (uint256)  {
      
      //id of the next open Index of LocationArray
      uint256 _newLocationID = locations.length; 
      
         Location memory _newLocation = Location({
          
          tokenname: _tokenname,
          tokensymbol: _tokensymbol,
          locationname: _locationname,
          locationaddress: _locationWalletAddress,
          locationID: _newLocationID
          
        });
        
        
        //adds the location to the location array
        locations.push(_newLocation);
        
        //returns the locationID for the creator of the location
        return _newLocationID;
      
  }
  
  
  
    //Generates a Token for a Location and sets the requestsaddress as the owner, gets called when Visitor wants a Token from Admin
    //returns the Id of the token for the requestsaddress

    function requestToken(string _locationname, uint256 _locationID,  address _locationaddress, address _requestaddress) internal returns (uint256){
    
    
    
        PovToken memory _newpovtoken = PovToken({
       
            locationname: _locationname,
            locationaddress: _locationaddress,
            locationID: _locationID
            
        });
    
    
        //safes token to public manager array
        uint256 newTokenId = povtokens.push(_newpovtoken) -1;
    
        //safes this tokenindex to given locationindex (hashmap)
        tokenIndexToLocationIndex[newTokenId] = _locationID;
     
        //sets requestsaddress as owner of token
        tokenIndexToOwner[newTokenId] = _requestaddress;
        
        //ups the totalBalance of address
        ownershipTokenCount[_requestaddress]++;
  
  
        return newTokenId;
  
  
}



}