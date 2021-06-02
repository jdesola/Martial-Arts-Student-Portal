DROP TABLE IF EXISTS schedule_schedule_slot;
DROP TABLE IF EXISTS schedule;
DROP TABLE IF EXISTS schedule_slot;
DROP TABLE IF EXISTS class;
DROP TABLE IF EXISTS student_enrollment;
DROP TABLE IF EXISTS student_check_in;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS email_address;
DROP TABLE IF EXISTS phone_number;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS family;
DROP TABLE IF EXISTS family_address;
DROP TABLE IF EXISTS rank_code;
DROP TABLE IF EXISTS program;

CREATE TABLE program(
        program_id SERIAL,
        name text,
        
        CONSTRAINT pk_program_id PRIMARY KEY(program_id)
        );
INSERT INTO program (name) VALUES ('Taekwondo'), ('Fitness'), ('Weapons'), ('Ninja Trix');

CREATE TABLE rank_code (
        rank_id SERIAL,
        name text,
        program_id integer,
        
        CONSTRAINT fk_program_id FOREIGN KEY(program_id) references program(program_id)
        );
        
 INSERT INTO rank_code (program_id, name) VALUES (1, 'Dragon White Belt'), (1, 'Panda White Belt'), (1, 'White Belt'), (1, 'Dragon Yellow Belt'), (1, 'Dragon Gold Belt'),
 (1, 'Dragon Orange Belt'), (1, 'Dragon Green Belt'), (1, 'Dragon Purple Belt'), (1, 'Panda Yellow Belt'), (1, 'Panda Gold Belt'), (1, 'Panda Orange Belt'), (1, 'Panda Green Belt'),
 (1, 'Panda Purple Belt'), (1, 'Panda Blue Belt'), (1, 'Panda Brown Belt'), (1, 'Panda Red Belt'), (1, 'Probationary Yellow Belt'), (1, 'Recommended Yellow Belt'), (1, 'Yellow Belt'),
 (1, 'Orange Belt'), (1, 'Senior Orange Belt'), (1, 'Green Belt'), (1, 'Senior Green Belt'), (1, 'Purple Belt'), (1, 'Senior Purple Belt'), (1, 'Blue Belt'), (1, 'Senior Blue Belt'), 
 (1, 'Brown Belt'), (1, 'Senior Brown Belt'), (1, 'Red Belt'), (1, 'Senior Red Belt'), (1, '1st Degree Probationary Black Belt'), (1, '1st Degree Recommended Black Belt'),
 (1, '1st Degree Decided Black Belt'), (1, '1st Degree Senior Black Belt'), (1, '2nd Degree Probationary Black Belt'), (1, '2nd Degree Decided Black Belt'),
 (1, '2nd Degree Senior Black Belt'), (1, '3rd Degree Probationary Black Belt'), (1, '3rd Degree Decided Black Belt'), (1, '3rd Degree Senior Black Belt'),
 (1, '4th Degree Black Belt'), (1, '4th Degree Senior Black Belt'), (1, '5th Degree Black Belt'), (1, '6th Degree Black Belt'), (1, '7th Degree Black Belt'),
 (1, '8th Degree Black Belt'), (1, '9th Degree Black Belt'), (3, 'White Sash'), (3, 'Yellow Sash'), (3, 'Green Sash'), (3, 'Blue Sash'), (3, 'Brown Sash'), 
 (3, 'Red Sash'), (3, 'Black Sash'), (3, '2nd Degree Black Sash'); 
 
 CREATE TABLE family(
        family_id SERIAL,
        family_name TEXT,
        active BOOLEAN,
        
        CONSTRAINT family_id_pk PRIMARY KEY(family_id)
        );
        
 INSERT INTO family (family_name) VALUES ('DeSola');      
  INSERT INTO family(family_name) VALUES ('Slomovitz');    
 
 
 CREATE TABLE address(
        address_id SERIAL,
        family_id INTEGER,
        street_address_1 TEXT,
        street_address_2 TEXT,
        city TEXT,
        state VARCHAR(2),
        postal_code VARCHAR(5),
        
        CONSTRAINT address_id_pk PRIMARY KEY(address_id),
        CONSTRAINT fk_address_family_id FOREIGN KEY(family_id) REFERENCES family(family_id)
        );

INSERT INTO address (family_id, street_address_1, city, state, postal_code) VALUES (1, '2896 Chimney Point Drive', 'Columbus', 'OH', '43231');        
 
 CREATE TABLE phone_number(
        phone_number_id SERIAL,
        family_id INTEGER,
        phone_number_type TEXT,
        phone_number VARCHAR(10),
        
        CONSTRAINT phone_number_id_pk PRIMARY KEY(phone_number_id),
        CONSTRAINT phone_number_type_check CHECK (phone_number_type IN ('CELL','HOME','WORK', 'OTHER')),
        CONSTRAINT phone_number_family_id_fk FOREIGN KEY(family_id) REFERENCES family(family_id)
        );

INSERT INTO phone_number (family_id, phone_number_type, phone_number) VALUES (1, 'CELL', '4198190234');
        
        CREATE TABLE email_address(
        email_address_id SERIAL,
        family_id INTEGER,
        email_address_type TEXT,
        email_address TEXT,
        
        CONSTRAINT email_address_id_pk PRIMARY KEY(email_address_id),
        CONSTRAINT email_address_type_check CHECK (email_address_type IN ('PERSONAL','WORK', 'OTHER')),
        CONSTRAINT email_address_family_id_fk FOREIGN KEY(family_id) REFERENCES family(family_id)
        );
        
 INSERT INTO email_address (family_id, email_address_type, email_address) VALUES (1, 'PERSONAL', 'joshdesola@gmail.com');
 
 CREATE TABLE student(
        student_id SERIAL,
        family_id INTEGER,
        first_name TEXT,
        preferred_name TEXT,
        last_name TEXT,
        birth_date DATE,
        
        CONSTRAINT pk_student_id PRIMARY KEY(student_id),
        CONSTRAINT fk_student_family_id FOREIGN KEY(family_id) references family(family_id)
        );
        
INSERT INTO student (family_id, first_name, preferred_name, last_name, birth_date) VALUES (1, 'Joshua', 'Josh', 'DeSola', '04/21/1992');
      
        
 CREATE TABLE student_enrollment(
        student_id integer,
        program_id integer,
        enrollment_date date,
        cancellation_date date,
        
        CONSTRAINT fk_student_enrollment_student_id FOREIGN KEY(student_id) references student(student_id),
        CONSTRAINT fk_student_enrollment_program_id FOREIGN KEY(program_id) references program(program_id)
        );
        
INSERT INTO student_enrollment (student_id, program_id, enrollment_date) VALUES (1, 1, '08/24/2010');         
        
CREATE TABLE student_check_in(
        student_id INTEGER,
        program_id INTEGER,
        check_in_date DATE,
        
        CONSTRAINT fk_student_check_in_student_id FOREIGN KEY(student_id) references student(student_id),
        CONSTRAINT fk_student_check_in_program_id FOREIGN KEY(program_id) references program(program_id)
        );
        
INSERT INTO student_check_in VALUES (1, 1, '05/24/2021');

CREATE TABLE class(
        class_id SERIAL,
        name TEXT,
        
        CONSTRAINT pk_class_id PRIMARY KEY(class_id)
        );
        
 INSERT INTO class (name) VALUES ('Advanced Taekwondo');
 
 CREATE TABLE schedule_slot(
        schedule_slot_id SERIAL,
        class_id INTEGER,
        day_of_week TEXT,
        start_time TIME,
        class_duration INTERVAL MINUTE,
        
        CONSTRAINT pk_schedule_slot_id PRIMARY KEY(schedule_slot_id),
        CONSTRAINT fk_schedule_slot_class_id FOREIGN KEY(class_id) references class(class_id)
        );
        
 INSERT INTO schedule_slot (class_id, day_of_week, start_time, class_duration) VALUES (1, 'Monday', '19:00', '30');              
 
 
 CREATE TABLE schedule(
        schedule_id SERIAL,
        name TEXT,
        active BOOLEAN,
        
        CONSTRAINT pk_schedule_id PRIMARY KEY(schedule_id)
        );
        
INSERT INTO schedule (name, active) VALUES ('Test', True);

 CREATE TABLE schedule_schedule_slot(
        schedule_id INTEGER,
        schedule_slot_id INTEGER,
        
        CONSTRAINT fk_schedule_schedule_slot_schedule_id FOREIGN KEY(schedule_id) references schedule(schedule_id),
        CONSTRAINT fk_schedule_schedule_slot_schedule_slot_id FOREIGN KEY(schedule_slot_id) references schedule_slot(schedule_slot_id)
        );
        
 INSERT INTO schedule_schedule_slot VALUES (1, 1);
