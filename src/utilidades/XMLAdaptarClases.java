/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import com.sun.jndi.cosnaming.IiopUrl.Address;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Atenas01
 */


/*public class ContactMethodAdapter extends
    XmlAdapter<ContactMethodAdapter.AdaptedContactMethod, ContactMethod> {
 
    @Override
    public AdaptedContactMethod marshal(ContactMethod contactMethod)
        throws Exception {
        if (null == contactMethod) {
            return null;
        }
        AdaptedContactMethod adaptedContactMethod = new AdaptedContactMethod();
        if (contactMethod instanceof Address) {
            Address address = (Address) contactMethod;
            adaptedContactMethod.street = address.street;
            adaptedContactMethod.city = address.city;
        } else {
            PhoneNumber phoneNumber = (PhoneNumber) contactMethod;
            adaptedContactMethod.number = phoneNumber.number;
        }
        return adaptedContactMethod;
    }
 
    @Override
    public ContactMethod unmarshal(AdaptedContactMethod adaptedContactMethod)
        throws Exception {
        if (null == adaptedContactMethod) {
            return null;
        }
        if (null != adaptedContactMethod.number) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.number = adaptedContactMethod.number;
            return phoneNumber;
        } else {
            Address address = new Address();
            address.street = adaptedContactMethod.street;
            address.city = adaptedContactMethod.city;
            return address;
        }
    }
    
}
*/