pragma solidity ^0.5.16;



contract TokenManager {
    

    constructor() public {
        
    }

    //Struct for a Location that wants to give out POV Tokens
    struct Location {
        string tokenname;
        string tokensymbol;
        string locationname;
        address locationaddress;
    }

    //Struct data for all POV-Tokens in existence
    struct PovToken {
        string locationname;
        address locationaddress;
    }

    //Storage for all Locations
    Location[] public locations;

    //Storage for all tokens from all locations in existence
    PovToken[] public povtokens;

    address[] public locationaddresses;

    //hashmaps

    //This mapping represents which Index of the povtokens map is owned by who (address)
    //ID is basically the same as the INDEX!! (as far as i understand)
    mapping(uint256 => address) public tokenIndexToOwnerAddress;

    mapping(address => uint256) public ownerAddressToLocationIndex;

    //used to see the total tokens of an address (needed for ERC721)
    mapping(address => uint256) public ownershipTokenCount;

    mapping(uint256 => string) public locationIDtoLocationName;

    //getters and setters for testing

    //theses getters are for the Struct Location, because you can't return a stuct to another contract in solidity
    function getLocationNameFromLocationAddress(address _address)
        public
        view
        returns (string memory locationname)
    {
        return locations[ownerAddressToLocationIndex[_address]].locationname;
    }

    function getTokenSymbolFromLocationAddress(address _address)
        public
        view
        returns (string memory tokensymbol)
    {
        return locations[ownerAddressToLocationIndex[_address]].tokensymbol;
    }

    function getTokennamefromLocationAddress(address _address)
        public
        view
        returns (string memory tokenname)
    {
        return locations[ownerAddressToLocationIndex[_address]].tokenname;
    }

    //gives the name of the location from given token
    function getLocationNameFromId(uint256 _id)
        public
        view
        returns (string memory locationname)
    {
        return povtokens[_id].locationname;
    }

    function getTokenNameFromId(uint256 _id)
        public
        view
        returns (string memory)
    {
        return getTokennamefromLocationAddress(povtokens[_id].locationaddress);
    }

    function getTokenSymbolFromId(uint256 _id)
        public
        view
        returns (string memory)
    {
        return
            getTokenSymbolFromLocationAddress(povtokens[_id].locationaddress);
    }

    function setLocationNameForId(uint256 _id, string memory _locationname)
        public
    {
        locationIDtoLocationName[_id] = _locationname;
    }

    function getAmountLocations() public view returns (uint256 amount) {
        return locations.length;
    }

    function getLocationAddressFromId(uint256 _id)
        public
        view
        returns (address locationaddress)
    {
        return povtokens[_id].locationaddress;
    }

    //end

    //creates a new location that can give out tokens to visitors, e.g. "IFIS-Token, Institut für Internetsicherheit"

    function createLocation(
        string calldata _tokenname,
        string calldata _tokensymbol,
        string calldata _locationname,
        address _locationWalletAddress
    ) external returns (uint256) {
        bool locationExists = false;
        bool locationnameExists = false;
        bool tokennameExists = false;
        bool tokensymbolExists = false;

        //keccak256 is for comparing strings
        for (uint256 i = 0; i < locations.length; i++) {
            if (locationaddresses[i] == _locationWalletAddress) {
                locationExists = true;
            }
            if (
                compareStrings(locations[i].locationname, _locationname)
            ) {
                locationnameExists = true;
            }
            if (compareStrings(locations[i].tokenname, _tokenname)) {
                tokennameExists = true;
            }
            if (compareStrings(locations[i].tokensymbol, _tokensymbol)) {
                tokensymbolExists = true;
            }
        }

        //missing else statements for caller to know what the error is
        if (
            locations.length == 0 ||
            (locationExists == false &&
                locationnameExists == false &&
                tokennameExists == false &&
                tokensymbolExists == false)
        ) {
            Location memory _newLocation = Location({
                tokenname: _tokenname,
                tokensymbol: _tokensymbol,
                locationname: _locationname,
                locationaddress: _locationWalletAddress
            });

            //adds the location to the location array
            locations.push(_newLocation);
            locationaddresses.push(_locationWalletAddress);
            ownerAddressToLocationIndex[_locationWalletAddress] =
                locations.length -
                1;
        }
    }

    //Generates a Token for a Location and sets the requestsaddress as the owner, gets called when Visitor wants a Token from Admin

    //locationId and locationaddress is given by admin

    //the function should return the tokenID to Admin to give it to requester. Right know i don't know how to do that

    function requestToken(address _locationaddress, address _requestaddress)
        external
    {
        PovToken memory _newpovtoken = PovToken({
            locationname: locations[ownerAddressToLocationIndex[_locationaddress]]
                .locationname,
            locationaddress: _locationaddress
        });

        //safes token to public manager array
        uint256 newTokenId = povtokens.push(_newpovtoken) - 1;

        //sets requestsaddress as owner of token
        tokenIndexToOwnerAddress[newTokenId] = _requestaddress;

        //ups the totalBalance of address
        ownershipTokenCount[_requestaddress]++;
    }

    //This function gets called from the userapp and returns a JsonObject containing all the information of the tokens a user(address) owns.
    //The final representation is done by the app itself
    function getUserTokenlist(address useraddress)
        public
        view
        returns (string memory jsonTokenList)
    {
        string memory jsonObject = "[";

        for (uint256 i = 0; i < povtokens.length; i++) {
            if (tokenIndexToOwnerAddress[i] == useraddress) {
                 jsonObject = strConcat(jsonObject, "{");

                 jsonObject = strConcat(jsonObject, '"locationaddress":"');
                jsonObject = strConcat(
                    jsonObject,
                    addressToString(getLocationAddressFromId(i))
                );
                jsonObject = strConcat(jsonObject, '",');

                jsonObject = strConcat(jsonObject, '"locationname":"');
                jsonObject = strConcat(jsonObject, getLocationNameFromId(i));
                jsonObject = strConcat(jsonObject, '",');

                jsonObject = strConcat(jsonObject, '"tokenname":"');
                jsonObject = strConcat(jsonObject, getTokenNameFromId(i));
                jsonObject = strConcat(jsonObject, '",');

                jsonObject = strConcat(jsonObject, '"tokensymbol":"');
                jsonObject = strConcat(jsonObject, getTokenSymbolFromId(i));
                jsonObject = strConcat(jsonObject, '",');

                jsonObject = strConcat(jsonObject, '"token":');
                jsonObject = strConcat(jsonObject, int2str(i));

                if (i == povtokens.length - 1) {
                    jsonObject = strConcat(jsonObject, '}');
                } else {
                    jsonObject = strConcat(jsonObject, '},');
                }
            }            
        }

        jsonObject = strConcat(jsonObject, "]");

        return jsonObject;
    }

    //Helperfunctions __________________________________________________________________________________________

    function compareStrings(string memory _a, string memory _b) private pure returns (bool) {
        return (keccak256(abi.encodePacked((_a))) == keccak256(abi.encodePacked((_b))) );
    }

    function strConcat(string memory s1, string memory s2)
        public
        pure
        returns (string memory)
    {
        return string(abi.encodePacked(s1, s2));
    }

    function addressToString(address x) public pure returns (string memory) {
        bytes memory s = new bytes(42);
        s[0] = "0";
        s[1] = "x";
        for (uint256 i = 0; i < 20; i++) {
            bytes1 b = bytes1(uint8(uint256(x) / (2**(8 * (19 - i)))));
            bytes1 hi = bytes1(uint8(b) / 16);
            bytes1 lo = bytes1(uint8(b) - 16 * uint8(hi));
            s[2 + 2 * i] = char(hi);
            s[3 + 2 * i] = char(lo);
        }
        return string(s);
    }

    function char(bytes1 b) public pure returns (bytes1 c) {
        if (uint8(b) < 10) return bytes1(uint8(b) + 0x30);
        else return bytes1(uint8(b) + 0x57);
    }

    function int2str(uint256 _i)
        internal
        pure
        returns (string memory _uintAsString)
    {
        if (_i == 0) {
            return "0";
        }
        uint256 j = _i;
        uint256 len;
        while (j != 0) {
            len++;
            j /= 10;
        }
        bytes memory bstr = new bytes(len);
        uint256 k = len - 1;
        while (_i != 0) {
            bstr[k--] = bytes1(uint8(48 + (_i % 10)));
            _i /= 10;
        }
        return string(bstr);
    }
}
   


contract ERC721 {
    // Required methods
    function totalSupply() public view returns (uint256 total);

    function balanceOf(address _owner) public view returns (uint256 balance);

    function ownerOf(uint256 _tokenId) external view returns (address owner);

    function approve(address _to, uint256 _tokenId) external;

    function transfer(address _to, uint256 _tokenId) external;

    function transferFrom(
        address _from,
        address _to,
        uint256 _tokenId
    ) external;

    // Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);

    // Optional
    // function name() public view returns (string name);
    // function symbol() public view returns (string symbol);
    // function tokensOfOwner(address _owner) external view returns (uint256[] tokenIds);
    // function tokenMetadata(uint256 _tokenId, string _preferredTransport) public view returns (string infoUrl);

    // ERC-165 Compatibility (https://github.com/ethereum/EIPs/issues/165)
    function supportsInterface(bytes4 _interfaceID)
        external
        view
        returns (bool);
}


contract POVToken is TokenManager, ERC721 {
    function totalSupply() public view returns (uint256) {
        return povtokens.length;
    }

    function totalSupplyLocations() public view returns (uint256) {
        return locations.length;
    }

    function ownerOf(uint256 _tokenID) external view returns (address owner) {
        return tokenIndexToOwnerAddress[_tokenID];
    }

    function balanceOf(address _owner) public view returns (uint256 balance) {
        return ownershipTokenCount[_owner];
    }

    // |1| Right now there is no plans to send or interchange tokens after the owner is declared. It's self-explanatory because a Token is a Proof of Visit, you can't give your "visit" to someone else

    //Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);

    //See |1|
    function transfer(address requestaddress, uint256 _tokenId) external {
        emit Transfer(msg.sender, requestaddress, _tokenId);
    }

    //See |1|
    function approve(address _to, uint256 _tokenId) external {
        emit Approval(msg.sender, _to, _tokenId);
    }

    //See |1|
    function transferFrom(
        address _from,
        address _to,
        uint256 _tokenId
    ) external {
        emit Transfer(_from, _to, _tokenId);
    }

    function supportsInterface(bytes4 _interfaceID)
        external
        view
        returns (bool)
    {
        return true;
    }
}
