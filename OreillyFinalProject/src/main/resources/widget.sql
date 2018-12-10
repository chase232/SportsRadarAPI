CREATE TABLE events (
	event_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	date_time TIMESTAMP NOT NULL,
	event_type VARCHAR(50) NOT NULL,
	sms_sent VARCHAR(1) NOT NULL,
	PRIMARY KEY(event_id)
);

CREATE TABLE event_properties (
	property_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	event_id BIGINT NOT NULL,
	event_key VARCHAR(50) NOT NULL,
	event_value VARCHAR(160) NOT NULL,
	PRIMARY KEY(property_id),
	CONSTRAINT event_properties_fk FOREIGN KEY(event_id) REFERENCES events(event_id)
);

--DROP TABLE events;
--DROP TABLE event_properties;


