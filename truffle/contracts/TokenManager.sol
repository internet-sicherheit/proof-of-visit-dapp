pragma solidity ^0.5.16;

    contract TokenManager{
        
        constructor() public {
            
        }

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
     Location[] public locations;
    
    //Storage for all tokens from all locations in existence
     PovToken [] public povtokens;


   
   //hashmaps
      
    //This mapping represents which Index of the povtokens map is owned by who (address)
    //ID is basically the same as the INDEX!! (as far as i understand)
    mapping (uint256 => address) public tokenIndexToOwner;
    

   
   //this mapping represents which location an token has
    mapping(uint256 => uint256) public tokenIndexToLocationIndex;
    
    //used to see the total tokens of an address (needed for ERC721)
    mapping (address => uint256) public ownershipTokenCount;
    
    mapping(uint256 => string) public locationIDtoLocationName;

 function getLocationNameFromId(uint256 _id) public view returns  (string memory)
    {
        return locationIDtoLocationName[_id];
    }

  function setLocationNameForId(uint256 _id, string memory _locationname) public 
    {
        locationIDtoLocationName[_id] = _locationname;
    }
    
    

    //creates a new location that can give out tokens to visitors, e.g. "IFIS-Token, Institut für Internetsicherheit"
    
    function createLocation(string calldata _tokenname, string calldata _tokensymbol, string calldata _locationname, address _locationWalletAddress ) external returns (uint256)  {
      
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
        
        locationIDtoLocationName[_newLocationID] = _locationname;
        
        //returns the locationID for the creator of the location
        return _newLocationID;
      
  }
  
  
  
    //Generates a Token for a Location and sets the requestsaddress as the owner, gets called when Visitor wants a Token from Admin
    //returns the Id of the token for the requestsaddress
    //locationaddress is currently unused but may be used in later implementations

    function requestToken(uint256 _locationID, address _locationaddress, address _requestaddress) external{
    
 
        PovToken memory _newpovtoken = PovToken({
       
            locationname: locationIDtoLocationName[_locationID],
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

}



}



contract ERC721 {
    // Required methods
    function totalSupply() public view returns (uint256 total);
    function balanceOf(address _owner) public view returns (uint256 balance);
    function ownerOf(uint256 _tokenId) external view returns (address owner);
    function approve(address _to, uint256 _tokenId) external;
    function transfer(address _to, uint256 _tokenId) external;
    function transferFrom(address _from, address _to, uint256 _tokenId) external;

    // Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);

    // Optional
    // function name() public view returns (string name);
    // function symbol() public view returns (string symbol);
    // function tokensOfOwner(address _owner) external view returns (uint256[] tokenIds);
    // function tokenMetadata(uint256 _tokenId, string _preferredTransport) public view returns (string infoUrl);

    // ERC-165 Compatibility (https://github.com/ethereum/EIPs/issues/165)
    function supportsInterface(bytes4 _interfaceID) external view returns (bool);
}




contract POVToken is TokenManager, ERC721 {
    
   
    
 
    
     function totalSupply() public view returns (uint) {
        return povtokens.length ;
    }
    
    
    
    
       function totalSupplyLocations() public view returns (uint) {
        return locations.length ;
    }
    
  
    
 
    
    
  
     function ownerOf(uint256 _tokenID) external view returns (address owner){
         
         return tokenIndexToOwner[_tokenID];
  
     }
     
  
   function balanceOf(address _owner) public view returns (uint256 balance)
   {
       
       return ownershipTokenCount[_owner];
       
   }
   
   
   //gives the name of the location from given token
   function locationNameOfToken(uint256 _tokenID) public view returns (string memory 
locationname)
   {
    
      return locations[tokenIndexToLocationIndex[_tokenID]].locationname;
  
   }
    
    
    
    
    
     // |1| Right now there is no plans to send or interchange tokens after the owner is declared. It's self-explanatory because a Token is a Proof of Visit, you can't give your "visit" to someone else
     
     //Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);
     
    //See |1|
    function  transfer(address requestaddress, uint256 _tokenId) external {
         
        
        emit Transfer(msg.sender, requestaddress, _tokenId);

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
    
    
  //  function supportsInterface(bytes4 _interfaceID) external view returns (bool){
        
 //       return true;
 //   }
    
    
   

}