pragma solidity ^0.4.0;

    contract Tokenmanager{

    //Struct for a Location that wants to give out POV Tokens
    struct Location{
        
        string tokenname;
        string tokensymbol;
        string locationname;
        address locationaddress;
        
    }
    

    //Struct data for all POV-Tokens in existence
    struct PovToken{
       
        string locationname;
        address locationaddress;
  
    }


    //Storage for all Locations 
    Location[] locations;
    
    //Storage for all tokens in existence,
    PovToken [] povtokens;


    //hab ich noch nicht ganz gecheckt, quasi ne hashmap
      
    //This mapping represents which Index of the povtokens map is owned by who (address)
    //ID is basically the same as the INDEX!! (as far as i understand)
    mapping (uint256 => address) public tokenIndexToOwner;
    

    //unused atm
    mapping(uint32 => address) public locationIndexToOwner;
    
    

}