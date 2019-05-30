select *
from contacts where fk_category = (select id from atags where title = "familly");

select contacts.id,
contacts.name,
contacts.number,
contacts.surname,
contacts.fk_category,
contacts.fk_address,
contacts.fk_ranking
from contacts
join contact_tag on contact_id = contact_tag.contact_id
join atags on contact_tag.tag_id = atags.id
where atags.id = (select id from atags where title= "teges")
