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
    

}